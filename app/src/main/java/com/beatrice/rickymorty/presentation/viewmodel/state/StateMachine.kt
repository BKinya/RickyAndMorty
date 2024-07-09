package com.beatrice.rickymorty.presentation.viewmodel.state

data class Output(val state: CharacterUiState, val sideEffect: CharacterSideEffect? = null)

class StateMachine {
    fun onEvent(characterEvent: CharacterEvent): Output {
        return when (characterEvent) {
            is CharacterEvent.OnFetchCharacters -> Output(
                state = CharacterUiState.Loading,
                sideEffect = CharacterSideEffect.FetchCharacters
            )

            is CharacterEvent.OnFetchingCharacters -> Output(
                state = CharacterUiState.CharacterPagedData(characterEvent.characters)
            )
        }
    }
}
