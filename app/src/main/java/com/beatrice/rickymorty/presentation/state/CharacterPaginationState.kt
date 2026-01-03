package com.beatrice.rickymorty.presentation.state

import com.beatrice.rickymorty.domain.model.Character

sealed interface CharacterPaginationState {
    data object Default : CharacterPaginationState
    data object InitialLoading : CharacterPaginationState

    data class Content(
        val characters: List<Character>,
        val nextPage: Int?
    ) : CharacterPaginationState

    data class InitialError(val message: String) : CharacterPaginationState
    data class LoadingMore(val currentItems: List<Character>) : CharacterPaginationState
    data class AppendError(val currentItems: List<Character>, val message: String) : CharacterPaginationState
}

sealed interface CharacterEvent {
    data object OnInitialFetchCharacters : CharacterEvent
    data class OnInitialFetchCharactersSuccess(val characters: List<Character>, val nextPage: Int?) : CharacterEvent
    data class OnInitialFetchCharactersFailure(val message: String) : CharacterEvent
    data class OnLoadMoreCharacters(val currentItems: List<Character>, val page: Int) : CharacterEvent
    data class OnLoadMoreCharactersSuccess(val characters: List<Character>, val nextPage: Int) : CharacterEvent
    data class OnLoadMoreCharactersFailure(val message: String) : CharacterEvent
}

sealed interface CharacterSideEffect {
    data object InitialFetchCharacters : CharacterSideEffect
    data class LoadMoreCharacters(val page: Int) : CharacterSideEffect
}
