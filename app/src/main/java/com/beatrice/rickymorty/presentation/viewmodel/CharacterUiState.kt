package com.beatrice.rickymorty.presentation.viewmodel

import com.beatrice.rickymorty.domain.model.Character

/**
 * What happens if I have 10 states?
 * Complex states and complex transitions
 *
 * If events are started everywhere in the UI code debugging becomes a challenge
 * Reproducing bugs... Time machine
 */
sealed interface CharacterUiState {
    data object Initial : CharacterUiState
    data object Loading : CharacterUiState

    @JvmInline value class Characters(val data: List<Character>) : CharacterUiState

    @JvmInline value class Error(val errorMessage: String) : CharacterUiState

    @JvmInline value class Empty(val message: String) : CharacterUiState
}
