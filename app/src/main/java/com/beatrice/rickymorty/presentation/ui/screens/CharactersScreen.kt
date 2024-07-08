package com.beatrice.rickymorty.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.presentation.ui.components.ShowBottomLoadingIndicator
import com.beatrice.rickymorty.presentation.ui.components.ShowCharactersList
import com.beatrice.rickymorty.presentation.ui.components.ShowErrorMessage
import com.beatrice.rickymorty.presentation.ui.components.ShowErrorMessageWithRefresh
import com.beatrice.rickymorty.presentation.ui.components.ShowLoadingIndicatorWithText

@Composable
fun CharactersScreen(
    uiState: LazyPagingItems<Character>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Scaffold(
        modifier = modifier.padding(16.dp),
        topBar = {
            Text(text = "Ricky and Morty")
        }
    ) { contentPadding ->
        val loadState = uiState.loadState
        if (loadState.refresh == LoadState.Loading) {
            ShowLoadingIndicatorWithText(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            )
        }

        ShowCharactersList(
            characters = uiState,
            modifier = Modifier.padding(contentPadding)
        )
        if (loadState.append == LoadState.Loading) {
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

        if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error) {
            val isPaginatingError = (loadState.append is LoadState.Error) || uiState.itemCount > 1
            val error = if (loadState.append is LoadState.Error) {
                (loadState.append as LoadState.Error).error
            } else {
                (loadState.refresh as LoadState.Error).error
            }
            if (isPaginatingError) {
                Box(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    ShowErrorMessage(
                        message = error.message ?: "Something went wrong",
                        modifier = Modifier
                            .padding(contentPadding)
                            .align(alignment = Alignment.BottomCenter)
                    )
                }
            } else {
                ShowErrorMessageWithRefresh(
                    message = error.message ?: "Something went wrong",
                    modifier = Modifier.fillMaxSize(),
                    onRetry = onRetry
                )
            }
        }
    }
}
