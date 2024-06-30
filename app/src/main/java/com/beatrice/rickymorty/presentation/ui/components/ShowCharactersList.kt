package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beatrice.rickymorty.domain.model.Character

@Composable
fun ShowCharactersList(
    modifier: Modifier = Modifier,
    characters: List<Character>
){
    LazyHorizontalGrid(
        modifier = modifier,
        rows = GridCells.Adaptive(128.dp)) {
        items(characters){ character ->
            CharacterComponent(character = character)
        }
    }

}
