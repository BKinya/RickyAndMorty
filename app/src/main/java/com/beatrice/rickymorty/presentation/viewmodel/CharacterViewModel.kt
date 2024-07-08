package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import com.beatrice.rickymorty.data.network.util.GENERAL_SERVER_ERROR
import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characterUiState: MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Initial)
    val characterUiState = _characterUiState.asStateFlow()




    fun getAllCharacters() {
        viewModelScope.launch(dispatcher) {
            characterRepository.getAllCharacters()
                .onStart {
                    _characterUiState.value = CharacterUiState.Loading
                }
                .collect { result ->
                    _characterUiState.value = CharacterUiState.Characters(data = flowOf(result))
                }
        }
    }
}
