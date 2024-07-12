package com.beatrice.rickymorty.presentation.state

import kotlinx.coroutines.flow.StateFlow

data class StateOutput<State, SideEffect>(val state: State, val sideEffect: SideEffect? = null)

interface StateMachine<State, Event, SideEffect>{

    val state: StateFlow<State>
    suspend fun accept(event: Event)

}
