package com.beatrice.rickymorty.presentation.state

interface StateReducer<State, Event, SideEffect> {
    fun reduce(currentState: State, event: Event): StateOutput<State, SideEffect?>
}
