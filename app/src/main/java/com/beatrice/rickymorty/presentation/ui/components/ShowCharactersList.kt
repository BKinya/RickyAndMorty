package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beatrice.rickymorty.presentation.state.CharacterPaginationState

@Composable
fun ShowCharactersList(
    modifier: Modifier = Modifier,
    uiState: CharacterPaginationState.Content,
    contePadding: PaddingValues,
    lazyGridState: LazyGridState
) {
    with(uiState) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            contentPadding = contePadding,
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            state = lazyGridState
        ) {
            items(characters) { character ->
                CharacterComponent(character = character)
            }

            uiState.errorMessage?.let {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ShowErrorMessage(message = it)
                }
            }
            if (uiState.isLoadingNextPage) {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ShowBottomLoadingIndicator(
                            modifier = Modifier.align(alignment = Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
