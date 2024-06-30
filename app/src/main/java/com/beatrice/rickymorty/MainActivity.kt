package com.beatrice.rickymorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.beatrice.rickymorty.presentation.theme.RickyMortyTheme
import com.beatrice.rickymorty.presentation.ui.screens.CharactersScreen
import com.beatrice.rickymorty.presentation.viewmodel.CharacterEvent
import com.beatrice.rickymorty.presentation.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       onFetchCharacters()
        setContent {
            val uiState = characterViewModel.pagedCharacters.collectAsLazyPagingItems()
            RickyMortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharactersScreen(
                        uiState = uiState,
                        onRetry = {
                            characterViewModel.getAllCharacters()
                        }
                    )
                }
            }
        }
    }

   private fun onFetchCharacters(){
       characterViewModel.onEvent(CharacterEvent.FetchAllCharacters)
   }
}
