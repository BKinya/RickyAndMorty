package com.beatrice.rickymorty.presentation.viewmodel.state

import androidx.paging.PagingData
import com.beatrice.rickymorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

sealed interface CharacterState {
    data object Initial : CharacterState
    data object Loading : CharacterState

    @JvmInline value class CharacterPagedData(val data: Flow<PagingData<Character>>) : CharacterState
}

sealed interface CharacterEvent {
    data object OnFetchCharacters : CharacterEvent

    @JvmInline value class OnFetchingCharacters(val characters: Flow<PagingData<Character>>) : CharacterEvent
}

sealed interface CharacterSideEffect {
    data object FetchCharacters : CharacterSideEffect
}
