package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterEvent
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterTimeTravelCapsule
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterUiState
import com.beatrice.rickymorty.presentation.viewmodel.state.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher,
    private val stateMachine: StateMachine
) : ViewModel() {


    private var characters: MutableStateFlow<PagingData<Character>> = MutableStateFlow(PagingData.empty())
    val pagedCharacters get() = characters.asStateFlow()

    private val timeCapsule = CharacterTimeTravelCapsule<CharacterUiState>()
    private val _characterUiState: MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Initial)
    val characterUiState = _characterUiState.asStateFlow()


    private val sideEffects: MutableStateFlow<CharacterSideEffect?> = MutableStateFlow(null)

    init {
        handleSideEffects()
    }

    fun sendEVent(event: CharacterEvent) {
        viewModelScope.launch(dispatcher) {
            val output = stateMachine.onEvent(event)

//            _characterUiState.value = output.state

            timeCapsule.addState(output.state)
            _characterUiState.value = output.state

            sideEffects.value = output.sideEffect
        }
    }

    private fun handleSideEffects() {
        viewModelScope.launch(dispatcher) {
            sideEffects.collect { effect ->
                when (effect) {
                    null -> {
                        // Do nothing
                    }
                    is CharacterSideEffect.FetchCharacters -> fetchAllCharacters()
                }
            }
        }
    }

    private fun fetchAllCharacters() {
        viewModelScope.launch(dispatcher) {
            characterRepository.getAllCharacters().collectLatest { data ->
                characters.value = data
            }
        }
    }
}
