package com.beatrice.rickymorty.presentation.state

import com.beatrice.rickymorty.domain.model.Character

sealed interface CharacterState {
    data object Initial : CharacterState
    data object Loading : CharacterState

    @JvmInline value class Characters(val data: List<Character>) : CharacterState

    @JvmInline value class Error(val errorMessage: String) : CharacterState

    @JvmInline value class Empty(val message: String) : CharacterState
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
