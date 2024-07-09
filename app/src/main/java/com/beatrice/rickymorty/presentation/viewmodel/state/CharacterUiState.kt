package com.beatrice.rickymorty.presentation.viewmodel.state

import androidx.paging.PagingData
import com.beatrice.rickymorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

sealed interface CharacterUiState {
    data object Initial : CharacterUiState
    data object Loading : CharacterUiState

    @JvmInline value class CharacterPagedData(val data: Flow<PagingData<Character>>) : CharacterUiState
}

sealed interface CharacterEvent {
    data object OnFetchCharacters : CharacterEvent

    @JvmInline value class OnFetchingCharacters(val characters: Flow<PagingData<Character>>) : CharacterEvent
}

sealed interface CharacterSideEffect {
    data object FetchCharacters : CharacterSideEffect
}
