package com.beatrice.rickymorty.presentation.viewmodel.state

import com.beatrice.rickymorty.domain.model.Character

sealed interface CharacterUiState {
    data object Initial : CharacterUiState
    data object Loading : CharacterUiState

    @JvmInline value class Characters(val data: List<Character>) : CharacterUiState

    @JvmInline value class Error(val errorMessage: String) : CharacterUiState

    @JvmInline value class Empty(val message: String) : CharacterUiState
}

/**
 *
 * If events are started everywhere in the UI code debugging becomes a challenge
 *
 * Also the UI should be dump and passive
 *
 * Reproducing bugs... Time machine
 */
sealed interface CharacterEvent {
    data object FetchAllCharacters : CharacterEvent

    /**
     * Does using value classes here even make sense?
     */
    @JvmInline value class FetchCharacterSuccessful(val characters: List<Character>) : CharacterEvent

    @JvmInline value class FetchCharacterFailed(val message: String) : CharacterEvent

    @JvmInline value class NoCharacterFound(val message: String) : CharacterEvent
}

/**
 * Side effect to handle business logic
 */
sealed interface CharacterSideEffect {
    data object FetchCharacters : CharacterSideEffect
}
