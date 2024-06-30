package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.rickymorty.data.network.util.GENERAL_SERVER_ERROR
import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characterUiState: MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Initial)
    val characterUiState = _characterUiState.asStateFlow()

    fun onEvent(characterEvent: CharacterEvent){
        when(characterEvent){
            is CharacterEvent.FetchAllCharacters -> onFetchAllCharacters()
        }
    }

    fun onFetchAllCharacters() {
        viewModelScope.launch(dispatcher) {
            characterRepository.getAllCharacters()
                .onStart {
                    _characterUiState.value = CharacterUiState.Loading
                }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val characters = result.data
                            if (characters == null) {
                                _characterUiState.value = CharacterUiState.Error(GENERAL_SERVER_ERROR)
                            } else if (characters.isEmpty()) {
                                _characterUiState.value = CharacterUiState.Empty(message = "No Characters found")
                            } else {
                                _characterUiState.value = CharacterUiState.Characters(data = characters)
                            }
                        }

                        is NetworkResult.Error -> _characterUiState.value = CharacterUiState.Error(result.errorMessage)
                        is NetworkResult.Exception -> _characterUiState.value = CharacterUiState.Error(result.message)
                    }
                }
        }
    }
}
