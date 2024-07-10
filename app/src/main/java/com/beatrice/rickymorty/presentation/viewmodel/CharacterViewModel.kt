package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterEvent
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterTimeTravelCapsule
import com.beatrice.rickymorty.presentation.viewmodel.state.CharacterState
import com.beatrice.rickymorty.presentation.viewmodel.state.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// TODO: Move this state to state machine
// Implement searching a character

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher,
    private val stateMachine: StateMachine<CharacterState, CharacterEvent, CharacterSideEffect>
) : ViewModel() {

    private val timeCapsule = CharacterTimeTravelCapsule<CharacterState>()
    private val sideEffects: MutableStateFlow<CharacterSideEffect?> = MutableStateFlow(null)

    init {
        handleSideEffects()
    }

    fun sendEVent(event: CharacterEvent) {
        viewModelScope.launch(dispatcher) {
            val output = stateMachine.onEvent(event)
            _characterState.value = output.state
            sideEffects.value = output.sideEffect

            timeCapsule.addState(output.state)
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
            val pagingData = characterRepository.getAllCharacters()
            sendEVent(CharacterEvent.OnFetchingCharacters(pagingData))
        }
    }
}
