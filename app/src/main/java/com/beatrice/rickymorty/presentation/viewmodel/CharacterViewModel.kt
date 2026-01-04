package com.beatrice.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.rickymorty.data.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterPaginationState
import com.beatrice.rickymorty.presentation.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.state.CharacterTimeTravelCapsule
import com.beatrice.rickymorty.presentation.state.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import logcat.logcat

class CharacterViewModel(
    val stateMachine: StateMachine<CharacterPaginationState, CharacterEvent, CharacterSideEffect>,
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val timeCapsule = CharacterTimeTravelCapsule<CharacterPaginationState>()

    init {
        observeStateMachine()
    }

    fun sendEvent(event: CharacterEvent) {
        logcat(tag = "UI_STATE_2", message = { "event is $event" })
        viewModelScope.launch(dispatcher) {
            stateMachine.accept(event)
        }
    }

    private fun handleSideEffect(effect: CharacterSideEffect?) {
        when (effect) {
            null -> { /* Do nothing */ }

            is CharacterSideEffect.InitialFetchCharacters -> initialFetchCharacters()
            is CharacterSideEffect.LoadMoreCharacters -> effect.page?.let {
                // Load more characters when the value of page is not null
                loadMoreCharacters(it)
            }
        }
    }

    private fun observeStateMachine() {
        viewModelScope.launch {
            stateMachine.state.collect { state ->
                timeCapsule.addState(state)
            }
        }

        viewModelScope.launch {
            stateMachine.sideEffect.collect { effect ->
                handleSideEffect(effect)
            }
        }

        viewModelScope.launch {
            if (stateMachine.state.value is CharacterPaginationState.Default) {
                sendEvent(CharacterEvent.OnInitialFetchCharacters)
            }
        }
    }

    private fun initialFetchCharacters() {
        viewModelScope.launch {
            characterRepository.fetchCharacters(1)
                .onSuccess { result ->
                    sendEvent(CharacterEvent.OnInitialFetchCharactersSuccess(result.characters, result.nextPage))
                }
                .onFailure {
                    logcat(tag = "Fetch-Characters-failed", message = { it.message ?: "Unknown error" })
                    sendEvent(CharacterEvent.OnInitialFetchCharactersFailure(it.message ?: "Unknown error"))
                }
        }
    }

    private fun loadMoreCharacters(page: Int) {
        viewModelScope.launch {
            characterRepository.fetchCharacters(page)
                .onFailure {
                    logcat(tag = "Loading-More-Characters-Failed", message = { it.message ?: "Unknown error" })
                    sendEvent(CharacterEvent.OnLoadMoreCharactersFailure(it.message ?: "Unknown error"))
                }
                .onSuccess { result ->
                    sendEvent(CharacterEvent.OnLoadMoreCharactersSuccess(result.characters, result.nextPage))
                }
        }
    }
}
