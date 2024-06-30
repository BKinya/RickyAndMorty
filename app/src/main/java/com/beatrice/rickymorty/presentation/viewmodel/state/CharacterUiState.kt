package com.beatrice.rickymorty.presentation.viewmodel.state

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

/**
 * The ui is dump and passive
 *
 * The ui should just emit events
 * and receive data
 */
sealed interface CharacterEvent{
    data object FetchAllCharacters: CharacterEvent

    /**
     * Does using value classes here even make sense?
     */
    @JvmInline value class FetchCharacterSuccessful(val characters: List<Character>): CharacterEvent
    @JvmInline value class FetchCharacterFailed(val message: String): CharacterEvent
    @JvmInline value class NoCharacterFound(val message: String): CharacterEvent
}

/**
 * Side effect to handle business logic
 */
sealed interface CharacterSideEffect {
    data object FetchCharacters : CharacterSideEffect
}
/**
 * TODO: Add a time machine
 */

/**
 * out transition functions will pure since they'll always return an instance of
 *  [Output] but the content of the object will vary
 */
