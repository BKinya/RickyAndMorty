package com.beatrice.rickymorty.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beatrice.rickymorty.presentation.ui.components.ShowCharactersPagedData
import com.beatrice.rickymorty.presentation.ui.components.ShowLoadingIndicatorWithText
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterUiState

@Composable
fun CharactersScreen(
    uiState: CharacterUiState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Scaffold(
        modifier = modifier.padding(16.dp),
        topBar = {
            Text(text = "Ricky and Morty")
        }
    ) { contentPadding ->
        when (uiState) {
            is CharacterUiState.Loading -> {
                ShowLoadingIndicatorWithText(
                    modifier = Modifier.padding(contentPadding)
                )
            }
            is CharacterUiState.CharacterPagedData -> {
                val characters = uiState.data
                ShowCharactersPagedData(
                    characters = characters,
                    onRetry = onRetry
                )
            }

            else -> {
                // do nothing
            }
        }
    }
}
