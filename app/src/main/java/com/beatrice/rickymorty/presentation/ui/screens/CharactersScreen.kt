package com.beatrice.rickymorty.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beatrice.rickymorty.presentation.ui.components.ShowCharactersList
import com.beatrice.rickymorty.presentation.ui.components.ShowErrorMessage
import com.beatrice.rickymorty.presentation.ui.components.ShowLoadingIndicator
import com.beatrice.rickymorty.presentation.state.CharacterUiState

@Composable
fun CharactersScreen(
    uiState: CharacterUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.padding(16.dp),
        topBar = {
            Text(text = "Ricky and Morty")
        }
    ) { contentPadding ->
        when (uiState) {
            is CharacterUiState.Loading -> ShowLoadingIndicator(
                modifier = Modifier.padding(contentPadding)
            )
            is CharacterUiState.Characters -> ShowCharactersList(
                characters = uiState.data,
                modifier = Modifier.padding(contentPadding)
            )
            is CharacterUiState.Error -> ShowErrorMessage(
                message = uiState.errorMessage,
                modifier = Modifier.padding(contentPadding)
            )

            is CharacterUiState.Empty -> ShowErrorMessage(
                message = uiState.message,
                textColor = Color.DarkGray,
                modifier = Modifier.padding(contentPadding)
            )
            else -> {
                // do nothing
            }
        }
    }
}
