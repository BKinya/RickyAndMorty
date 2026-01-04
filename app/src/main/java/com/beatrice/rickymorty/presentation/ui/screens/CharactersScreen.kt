package com.beatrice.rickymorty.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterPaginationState
import com.beatrice.rickymorty.presentation.ui.components.ShowCharactersList
import com.beatrice.rickymorty.presentation.ui.components.ShowErrorMessage
import com.beatrice.rickymorty.presentation.ui.components.ShowLoadingIndicatorWithText
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun CharactersScreen(
    uiState: CharacterPaginationState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onLoadMoreCharacters: (CharacterEvent) -> Unit
) {
    val lazyGridState = rememberLazyGridState()

    val currentState by rememberUpdatedState(uiState)
    println("State_is_ $currentState")
    LaunchedEffect(lazyGridState) {
        snapshotFlow {
            val layoutInfo = lazyGridState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9
            val totalItemsCount = layoutInfo.totalItemsCount
            val isNearEnd = lastVisibleIndex >= totalItemsCount - 2

            isNearEnd
        }
            .distinctUntilChanged()
            .collect { isNearEnd->
                val latestState = currentState
                if (isNearEnd){
                    if (isNearEnd && latestState is CharacterPaginationState.Content ) {
                        onLoadMoreCharacters(
                            CharacterEvent.OnLoadMoreCharacters(
                                currentItems = latestState.characters,
                                page = latestState.nextPage
                            )
                        )
                    }
                }

            }
    }
    Scaffold(
        topBar = {
            Text(
                text = "Ricky and Morty",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(12.dp)
            )
        }
    ) { contentPadding ->
        when (uiState) {
            is CharacterPaginationState.InitialLoading -> {
                ShowLoadingIndicatorWithText(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }

            is CharacterPaginationState.InitialError -> ShowErrorMessage(message = uiState.message, modifier = Modifier.fillMaxSize())
            is CharacterPaginationState.Content -> ShowCharactersList(
                uiState = uiState,
                contePadding = contentPadding,
                lazyGridState = lazyGridState,
                modifier = Modifier.padding(16.dp)
            )
            else -> { /* Do Nothing */ }
        }
    }
}
