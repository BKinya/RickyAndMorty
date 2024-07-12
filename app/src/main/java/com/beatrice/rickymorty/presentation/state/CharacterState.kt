package com.beatrice.rickymorty.presentation.state

import com.beatrice.rickymorty.domain.model.Character

sealed interface CharacterUiState {
    data object Initial : CharacterUiState
    data object Loading : CharacterUiState

    @JvmInline value class Characters(val data: List<Character>) : CharacterUiState

    @JvmInline value class Error(val errorMessage: String) : CharacterUiState

    @JvmInline value class Empty(val message: String) : CharacterUiState
}

sealed interface CharacterEvent {
    data object FetchAllCharacters : CharacterEvent

    @JvmInline value class FetchCharacterSuccessful(val characters: List<Character>) :
        CharacterEvent

    @JvmInline value class FetchCharacterFailed(val message: String) : CharacterEvent

    @JvmInline value class NoCharacterFound(val message: String) : CharacterEvent
}

sealed interface CharacterSideEffect {
    data object FetchCharacters : CharacterSideEffect
}
