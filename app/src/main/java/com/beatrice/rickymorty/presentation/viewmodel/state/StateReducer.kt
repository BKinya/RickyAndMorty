package com.beatrice.rickymorty.presentation.viewmodel.state

interface StateReducer<State, Event, SideEffect> {
    fun reduce(currentState: State, event: Event): StateOutput<State, SideEffect?>
}
