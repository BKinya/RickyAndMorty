package com.beatrice.rickymorty.presentation.state

import com.beatrice.rickymorty.presentation.state.CharacterSideEffect.*

class CharacterReducer : StateReducer<CharacterPaginationState, CharacterEvent, CharacterSideEffect> {
    override fun reduce(currentState: CharacterPaginationState, event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> {
        return when (currentState) {
            is CharacterPaginationState.Default -> currentState.reduce(event)
            is CharacterPaginationState.InitialLoading -> currentState.reduce(event)
            is CharacterPaginationState.Content -> currentState.reduce(event)
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
            StateOutput(state = CharacterPaginationState.Content(event.characters, nextPage = event.nextPage, isLoadingNextPage = false))

        else -> StateOutput(this, sideEffect = null)
    }

    private fun CharacterPaginationState.Content.reduce(event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> = when (event) {
        is CharacterEvent.OnLoadMoreCharacters -> StateOutput(
            this.copy(isLoadingNextPage = true),
            LoadMoreCharacters(page = event.page)
        )

        is CharacterEvent.OnLoadMoreCharactersFailure -> StateOutput(
            this.copy(isLoadingNextPage = false, errorMessage = event.message),

            )

        is CharacterEvent.OnLoadMoreCharactersSuccess -> {
            val oldState = this
            StateOutput(
                this.copy(isLoadingNextPage = false, characters = oldState.characters + event.characters),
            )
        }

        else -> StateOutput(this, sideEffect = null)
    }

}
