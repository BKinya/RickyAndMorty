package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.beatrice.rickymorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

@Composable
fun ShowCharactersList(
    modifier: Modifier = Modifier,
    characters: Flow<PagingData<Character>>
) {
    val characterPagingItems = characters.collectAsLazyPagingItems()
    characterPagingItems.loadState.refresh

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(characterPagingItems.itemCount) { index->
            val character = characterPagingItems[index]
            character?. let {
                CharacterComponent(character =character )
            }
        }
    }
}
