package com.beatrice.rickymorty.presentation.viewmodel.state

class CharacterReducer: StateReducer<CharacterState, CharacterEvent, CharacterSideEffect> {
    override fun reduce(currentState: CharacterState, event: CharacterEvent): StateOutput<CharacterState, CharacterSideEffect?> {
        return when (event) {
            is CharacterEvent.OnFetchCharacters -> StateOutput(
                state = CharacterState.Loading,
                sideEffect = CharacterSideEffect.FetchCharacters
            )

            is CharacterEvent.OnFetchingCharacters -> StateOutput(
                state = CharacterState.CharacterPagedData(event.characters)
            )
        }
    }


}
