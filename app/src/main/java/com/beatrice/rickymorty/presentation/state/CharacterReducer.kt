package com.beatrice.rickymorty.presentation.state

import com.beatrice.rickymorty.presentation.state.CharacterSideEffect.*

class CharacterReducer : StateReducer<CharacterPaginationState, CharacterEvent, CharacterSideEffect> {
    override fun reduce(currentState: CharacterPaginationState, event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> {
        return when (currentState) {
            is CharacterPaginationState.Default -> currentState.reduce(event)
            is CharacterPaginationState.InitialLoading -> currentState.reduce(event)
            is CharacterPaginationState.Content -> currentState.reduce(event)
            is CharacterPaginationState.LoadingMore -> currentState.reduce(event)
            else -> StateOutput(currentState)
        }
    }

    private fun CharacterPaginationState.Default.reduce(event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> = when (event) {
        is CharacterEvent.OnInitialFetchCharacters ->
            StateOutput(CharacterPaginationState.InitialLoading, InitialFetchCharacters)

        else -> StateOutput(this)
    }

    private fun CharacterPaginationState.InitialLoading.reduce(event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> = when (event) {
        is CharacterEvent.OnInitialFetchCharactersFailure ->
            StateOutput(state = CharacterPaginationState.InitialError(event.message))

        is CharacterEvent.OnInitialFetchCharactersSuccess ->
            StateOutput(state = CharacterPaginationState.Content(event.characters, nextPage = event.nextPage))

        else -> StateOutput(this, sideEffect = null)
    }

    private fun CharacterPaginationState.Content.reduce(event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> = when (event) {
        is CharacterEvent.OnLoadMoreCharacters -> StateOutput(
            CharacterPaginationState.LoadingMore(event.currentItems),
            LoadMoreCharacters(page = event.page)
        )

        else -> StateOutput(this, sideEffect = null)
    }

    private fun CharacterPaginationState.LoadingMore.reduce(event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> = when (event) {
        is CharacterEvent.OnLoadMoreCharactersFailure ->
            StateOutput(CharacterPaginationState.AppendError(this.currentItems, event.message))

        is CharacterEvent.OnLoadMoreCharactersSuccess -> StateOutput(
            state = CharacterPaginationState.Content(this.currentItems + event.characters, nextPage = event.nextPage)
        )

        else -> StateOutput(this, sideEffect = null)
    }
}
