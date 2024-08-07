package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.beatrice.rickymorty.domain.model.Character

@Composable
fun ShowCharactersList(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<Character>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(characters.itemCount) { index ->
            val character = characters[index]
            character?. let {
                CharacterComponent(character = character)
            }
        }
    }
}
