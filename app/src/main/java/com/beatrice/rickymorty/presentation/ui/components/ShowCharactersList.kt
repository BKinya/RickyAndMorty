package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.presentation.state.CharacterPaginationState

@Composable
fun ShowCharactersList(
    modifier: Modifier = Modifier,
    uiState: CharacterPaginationState,
    characters: List<Character>,
    contePadding: PaddingValues,
    lazyGridState: LazyGridState
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = contePadding,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        state = lazyGridState
    ) {
        items(characters){ character ->
            CharacterComponent(character = character)
        }

        when(uiState){
            is CharacterPaginationState.AppendError -> {
                item {
                    ShowErrorMessage(message = uiState.message)
                }
            }
            is CharacterPaginationState.LoadingMore -> {
                println("showinggg loading moreeee")
                item{
                    ShowBottomLoadingIndicator()
                }
            }
            else -> { /*Do nothing*/ }
        }
    }
}
