package com.beatrice.rickymorty.presentation.state

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn

data class Output(val state: CharacterState, val sideEffect: CharacterSideEffect? = null)

class DefaultStateMachine<State, Event, SideEffect>(
    private val context: CoroutineContext,
    private val initialState: State,
    private val reducer: StateReducer<State, Event, SideEffect>
): StateMachine<State, Event, SideEffect> {

    private val coroutineScope = CoroutineScope(context)

    private val eventStream = MutableSharedFlow<Event>(
        replay = 10,
        extraBufferCapacity = 10
    )

    fun onEvent(characterEvent: CharacterEvent): Output {
        return when (characterEvent) {
            is CharacterEvent.FetchAllCharacters -> Output(
                state = CharacterState.Loading,
                sideEffect = CharacterSideEffect.FetchCharacters
            )

            is CharacterEvent.FetchCharacterSuccessful -> Output(
                state = CharacterState.Characters(data = characterEvent.characters)
            )
            is CharacterEvent.FetchCharacterFailed -> Output(
                state = CharacterState.Error(errorMessage = characterEvent.message)
            )
            is CharacterEvent.NoCharacterFound -> Output(
                state = CharacterState.Empty(message = characterEvent.message)
            )
        }
    }

    override val state: StateFlow<StateOutput<State, SideEffect?>>
        get() = eventStream
            .scan(initial = StateOutput(initialState, null)) { (state, _): StateOutput<State, SideEffect?>, event: Event ->
                reducer.reduce(state, event)
            }
            .stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = StateOutput(initialState, null))


    override suspend fun accept(event: Event) {
        eventStream.emit(event)
    }
}
