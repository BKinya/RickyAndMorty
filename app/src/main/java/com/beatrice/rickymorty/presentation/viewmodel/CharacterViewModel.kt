package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.state.CharacterState
import com.beatrice.rickymorty.presentation.state.CharacterTimeTravelCapsule
import com.beatrice.rickymorty.presentation.state.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(
    val stateMachine: StateMachine<CharacterState, CharacterEvent, CharacterSideEffect>,
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher
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

    private fun handleSideEffect(sideEffect: CharacterSideEffect?) {
        when (sideEffect) {
            null -> {
                // Do nothing
            }

            is CharacterSideEffect.FetchCharacters -> fetchAllCharacters()
        }
    }

    private fun receiveState() {
        viewModelScope.launch(dispatcher) {
            stateMachine.state.collectLatest { output ->
                val state = output.state
                timeCapsule.addState(state)
                val sideEffect = output.sideEffect
                handleSideEffect(sideEffect)
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
