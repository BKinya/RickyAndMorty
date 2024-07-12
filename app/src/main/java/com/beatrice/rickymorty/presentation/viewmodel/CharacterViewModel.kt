package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.state.CharacterTimeTravelCapsule
import com.beatrice.rickymorty.presentation.state.CharacterState
import com.beatrice.rickymorty.presentation.state.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher,
     val stateMachine: StateMachine<CharacterState, CharacterEvent, CharacterSideEffect>
) : ViewModel() {

    private val timeCapsule = CharacterTimeTravelCapsule<CharacterState>()


    init {
        receiveState()
    }

    fun sendEVent(event: CharacterEvent) {
        viewModelScope.launch(dispatcher) {
            stateMachine.accept(event)
        }
    }
    private fun receiveState() {
        viewModelScope.launch(dispatcher) {
            stateMachine.state.collectLatest { output ->
                val state = output.state
                timeCapsule.addState(state)
                val sideEffect = output.sideEffect
                handleSideEffects(sideEffect)
            }
        }
    }

    private fun handleSideEffects(sideEffect: CharacterSideEffect?) {
        viewModelScope.launch(dispatcher) {
            when(sideEffect){
                CharacterSideEffect.FetchCharacters -> fetchAllCharacters()
                null -> {
                    // Do nothing
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
