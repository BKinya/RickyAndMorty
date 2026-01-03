package com.beatrice.rickymorty.presentation.state

class CharacterReducer : StateReducer<CharacterPaginationState, CharacterEvent, CharacterSideEffect> {
    override fun reduce(currentState: CharacterPaginationState, event: CharacterEvent): StateOutput<CharacterPaginationState, CharacterSideEffect?> {
        return when (event) {
            is CharacterEvent.OnInitialFetchCharacters -> StateOutput(
                state = CharacterPaginationState.InitialLoading,
                sideEffect = CharacterSideEffect.FetchCharacters
            )

            is CharacterEvent.OnInitialFetchCharactersSuccess -> StateOutput(
                state = CharacterPaginationState.CharacterPagedData(event.characters)
            )
        }
    }
}
