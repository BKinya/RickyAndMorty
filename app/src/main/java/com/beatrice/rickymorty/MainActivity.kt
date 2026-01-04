package com.beatrice.rickymorty

import android.R.attr.value
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterPaginationState
import com.beatrice.rickymorty.presentation.state.StateOutput
import com.beatrice.rickymorty.presentation.theme.RickyMortyTheme
import com.beatrice.rickymorty.presentation.ui.screens.CharactersScreen
import com.beatrice.rickymorty.presentation.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val characterPaginationState by characterViewModel
                .stateMachine
                .state
                .collectAsStateWithLifecycle(initialValue = CharacterPaginationState.Default)
            RickyMortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharactersScreen(
                        uiState = characterPaginationState,
                        onRetry = {/* TODO: Implement */ },
                        onLoadMoreCharacters = characterViewModel::sendEvent
                    )
                }
            }
        }
    }
}
