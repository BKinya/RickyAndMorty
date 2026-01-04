package com.beatrice.rickymorty.presentation.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultStateMachine<State, Event, SideEffect>(
    private val initialState: State,
    private val reducer: StateReducer<State, Event, SideEffect>
) : StateMachine<State, Event, SideEffect> {

    private val _sideEffect = MutableSharedFlow<SideEffect>(replay = 0, extraBufferCapacity = 64)
    override val sideEffect: Flow<SideEffect> = _sideEffect.asSharedFlow()

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<State> = _state.asStateFlow()

    override suspend fun accept(event: Event) {
        val outPut = reducer.reduce(_state.value, event)

        _state.value = outPut.state

        outPut.sideEffect?.let {
            _sideEffect.emit(it)
        }
    }
}
