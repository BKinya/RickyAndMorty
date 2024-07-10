package com.beatrice.rickymorty.presentation.viewmodel.state

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class DefaultStateMachine<State, Event, SideEffect>(
    private val coroutineContext: CoroutineContext,
    private  val initialState: State,
    private val reducer: StateReducer<State, Event, SideEffect>
    ) : StateMachine<State, Event, SideEffect> {

    private val coroutineScope = CoroutineScope(coroutineContext)
    private val eventStream = MutableSharedFlow<Event>(extraBufferCapacity = 10)


    override val state: StateFlow<StateOutput<State, SideEffect?>>
        get() = eventStream
            .scan(initial = StateOutput(initialState, null)) { (state, _): StateOutput<State, SideEffect?>, event: Event ->
                reducer.reduce(state, event)
            }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = StateOutput(initialState, null))


    override fun accept(event: Event) {
        coroutineScope.launch {
            eventStream.emit(value = event)

        }
    }
}
