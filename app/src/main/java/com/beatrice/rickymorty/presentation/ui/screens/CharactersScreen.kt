package com.beatrice.rickymorty.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.rickymorty.presentation.ui.components.ShowCharactersPagedData
import com.beatrice.rickymorty.presentation.ui.components.ShowLoadingIndicatorWithText
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterState

@Composable
fun CharactersScreen(
    uiState: CharacterState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
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
            is CharacterState.Loading -> {
                ShowLoadingIndicatorWithText(
                    modifier = modifier.padding(contentPadding)
                )
            }
            is CharacterState.CharacterPagedData -> {
                val characters = uiState.data
                ShowCharactersPagedData(
                    characters = characters,
                    onRetry = onRetry,
                    modifier = modifier.padding(contentPadding)
                )
            }

            else -> {
                // do nothing
            }
        }
    }
}
