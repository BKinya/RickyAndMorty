package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.state.CharacterTimeTravelCapsule
import com.beatrice.rickymorty.presentation.state.CharacterUiState
import com.beatrice.rickymorty.presentation.state.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher,
    private val stateMachine: StateMachine
) : ViewModel() {

    private val timeCapsule = CharacterTimeTravelCapsule<CharacterUiState>()

    private val _characterUiState: MutableStateFlow<CharacterUiState> = MutableStateFlow(
        CharacterUiState.Initial)
    val characterUiState = _characterUiState.asStateFlow()

    private val sideEffects: MutableStateFlow<CharacterSideEffect?> = MutableStateFlow(null)

    init {
        handleSideEffects()
    }

    fun sendEVent(event: CharacterEvent) {
        viewModelScope.launch(dispatcher) {
            val output = stateMachine.onEvent(event)
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
            characterRepository.getAllCharacters()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val characters = result.data
                            sendEVent(CharacterEvent.FetchCharacterSuccessful(characters = characters))
                        }

                        is NetworkResult.Error -> sendEVent(CharacterEvent.FetchCharacterFailed(result.errorMessage))
                        is NetworkResult.Exception -> sendEVent(CharacterEvent.FetchCharacterFailed(result.message))
                    }
                }
        }
    }
}
