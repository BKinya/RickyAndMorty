package com.beatrice.rickymorty.presentation.viewmodel.state

data class Output(val state: CharacterUiState, val sideEffect: CharacterSideEffect? = null)

class StateMachine {
    /**
     * functionally pure transitions
     */
    fun onEvent(characterEvent: CharacterEvent): Output {
        return when (characterEvent) {
            is CharacterEvent.FetchAllCharacters -> Output(
                state = CharacterUiState.Loading,
                sideEffect = CharacterSideEffect.FetchCharacters
            )

            is CharacterEvent.FetchCharacterSuccessful -> Output(
                state = CharacterUiState.Characters(data = characterEvent.characters)
            )
            is CharacterEvent.FetchCharacterFailed -> Output(
                state = CharacterUiState.Error(errorMessage = characterEvent.message)
            )
            is CharacterEvent.NoCharacterFound -> Output(
                state = CharacterUiState.Empty(message = characterEvent.message)
            )
        }
    }
}
