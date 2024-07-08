package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var characters: MutableStateFlow<PagingData<Character>> = MutableStateFlow(PagingData.empty())
    val pagedCharacters get() = characters.asStateFlow()

    fun getAllCharacters() {
        viewModelScope.launch(dispatcher) {
            characterRepository.getAllCharacters().collectLatest { data ->
                characters.value = data
            }
        }
    }
}
