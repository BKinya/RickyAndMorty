package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.beatrice.rickymorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

@Composable
fun ShowCharactersPagedData(
    characters: Flow<PagingData<Character>>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val characterState = characters.collectAsLazyPagingItems()
    val loadState = characterState.loadState
    val pagingState = loadState.getState()
    ShowCharactersList(
        characters = characterState,
        modifier = Modifier.padding(16.dp)
    )
    when (pagingState) {
        is CharacterPagingState.Error -> {
            ShowErrorMessageWithRefresh(
                message = pagingState.message,
                modifier = Modifier.fillMaxSize(),
                onRetry = onRetry
            )
        }
        is CharacterPagingState.ErrorWithData -> {
            Box(modifier = Modifier.fillMaxHeight()) {
                ShowErrorMessage(
                    message = pagingState.message,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.BottomCenter)
                )
            }
        }
        CharacterPagingState.Loading -> {
            ShowLoadingIndicatorWithText(
                modifier = Modifier
                    .fillMaxSize()

            )
        }
        CharacterPagingState.LoadingMoreCharacters -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                ShowBottomLoadingIndicator(
                    modifier.align(alignment = Alignment.BottomCenter)
                )
            }
        }

        CharacterPagingState.Default -> {
            // DO nothing
        }
    }
}

sealed interface CharacterPagingState {
    data object Default : CharacterPagingState
    data object Loading : CharacterPagingState
    data object LoadingMoreCharacters : CharacterPagingState

    @JvmInline value class Error(val message: String) : CharacterPagingState

    @JvmInline value class ErrorWithData(val message: String) : CharacterPagingState
}
fun CombinedLoadStates.getState(): CharacterPagingState {
    val loadState = this
    var state: CharacterPagingState = CharacterPagingState.Default
    if (loadState.refresh == LoadState.Loading) {
        state = CharacterPagingState.Loading
    }
    if (loadState.append == LoadState.Loading) {
        state = CharacterPagingState.LoadingMoreCharacters
    }

    if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error) {
        val isPaginatingError = (loadState.append is LoadState.Error)
        val error = if (loadState.append is LoadState.Error) {
            (loadState.append as LoadState.Error).error
        } else {
            (loadState.refresh as LoadState.Error).error
        }

        if (isPaginatingError) {
            state = CharacterPagingState.ErrorWithData(message = error.message ?: "Something Went Wrong")
        } else {
            state = CharacterPagingState.Error(message = error.message ?: "Something Went Wrong")
        }
    }
    return state
}
