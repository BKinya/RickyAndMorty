package com.beatrice.rickymorty.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
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

@Composable
fun CharactersScreen(
    uiState: CharacterPaginationState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onLoadMoreCharacters: (CharacterEvent) -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    val shouldLoadNextPage = remember {
        derivedStateOf {
            uiState is CharacterPaginationState.Content &&
                    ((lazyColumnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9) >= (lazyColumnState.layoutInfo.totalItemsCount - 19))
        }
    }

    LaunchedEffect(shouldLoadNextPage) {
        val state = uiState as? CharacterPaginationState.Content
        state?.let {
            onLoadMoreCharacters(
                CharacterEvent.OnLoadMoreCharacters(
                    currentItems = it.characters,
                    page = it.nextPage
                )
            )
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
                    modifier = modifier.padding(contentPadding)
                )
            }

            is CharacterPaginationState.InitialError -> ShowErrorMessage(message = uiState.message)
            is CharacterPaginationState.Content -> ShowCharactersList(uiState = uiState, characters = uiState.characters)
            else -> {/*Do nothing*/
            }
        }
    }
}
