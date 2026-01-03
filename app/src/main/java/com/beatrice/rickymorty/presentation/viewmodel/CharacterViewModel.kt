package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.rickymorty.data.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.state.CharacterPaginationState
import com.beatrice.rickymorty.presentation.state.CharacterTimeTravelCapsule
import com.beatrice.rickymorty.presentation.state.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import logcat.logcat

class CharacterViewModel(
    val stateMachine: StateMachine<CharacterPaginationState, CharacterEvent, CharacterSideEffect>,
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val timeCapsule = CharacterTimeTravelCapsule<CharacterPaginationState>()

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

            is CharacterSideEffect.InitialFetchCharacters -> initialFetchCharacters()
            is CharacterSideEffect.LoadMoreCharacters -> loadMoreCharacters(sideEffect.page)
        }
    }

    private fun receiveState() {
        viewModelScope.launch {
            stateMachine.state.collectLatest { output ->
                val state = output.state
                timeCapsule.addState(state)
                val sideEffect = output.sideEffect
                handleSideEffect(sideEffect)
            }
        }
    }

    private fun initialFetchCharacters() {
        viewModelScope.launch {
            characterRepository.fetchCharacters(1)
                .onSuccess { result ->
                    sendEVent(CharacterEvent.OnInitialFetchCharactersSuccess(result.characters, result.nextPage))
                }
                .onFailure {
                    logcat(tag = "Fetch-Characters-failed", message = { it.message ?: "Unknown error" })
                    sendEVent(CharacterEvent.OnInitialFetchCharactersFailure(it.message ?: "Unknown error"))
                }
        }
    }

    private fun loadMoreCharacters(page: Int){
        viewModelScope.launch {
            characterRepository.fetchCharacters(page)
                .onFailure {
                    logcat(tag = "Loading-More-Characters-Failed", message = { it.message ?: "Unknown error" })
                    sendEVent(CharacterEvent.OnLoadMoreCharactersFailure(it.message ?: "Unknown error"))
                }
                .onSuccess { result ->
                    sendEVent(CharacterEvent.OnInitialFetchCharactersSuccess(result.characters, result.nextPage))
                }
        }
    }
}
