package com.beatrice.rickymorty.viewmodel

import app.cash.turbine.test
import com.beatrice.rickymorty.data.network.util.GENERAL_SERVER_ERROR
import com.beatrice.rickymorty.presentation.viewmodel.CharacterViewModel
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterState
import com.beatrice.rickymorty.presentation.state.StateMachine
import com.beatrice.rickymorty.util.fakes.FakeCharacterRepository
import com.beatrice.rickymorty.util.resources.characters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    private val repository = FakeCharacterRepository()
    private val characterViewModel = CharacterViewModel(
        characterRepository = repository,
        dispatcher = UnconfinedTestDispatcher(),
        stateMachine = StateMachine()
    )

    @Test
    fun `test ui state updates correctly when fetching characters is successful`() = runTest {
        characterViewModel.characterUiState.test {
            assertEquals(CharacterState.Initial, awaitItem())
            characterViewModel.sendEVent(CharacterEvent.FetchAllCharacters)
            assertEquals(CharacterState.Loading, awaitItem())
            assertEquals(CharacterState.Characters(characters), awaitItem())
        }
    }

    @Test
    fun `test ui state updates correctly when fetching characters fails`() = runTest {
        repository.isRequestSuccessful = false
        characterViewModel.characterUiState.test {
            assertEquals(CharacterState.Initial, awaitItem())
            characterViewModel.sendEVent(CharacterEvent.FetchAllCharacters)
            assertEquals(CharacterState.Loading, awaitItem())
            assertEquals(CharacterState.Error(GENERAL_SERVER_ERROR), awaitItem())
        }
    }
}
