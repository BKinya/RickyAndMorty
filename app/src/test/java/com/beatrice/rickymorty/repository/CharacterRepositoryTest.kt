package com.beatrice.rickymorty.repository

import com.beatrice.rickymorty.data.network.util.GENERAL_SERVER_ERROR
import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.data.repository.CharacterRepositoryImpl
import com.beatrice.rickymorty.util.fakes.FakeCharacterService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterRepositoryTest {
    private val apiService = FakeCharacterService()

    private val repository = CharacterRepositoryImpl(apiService = apiService)

    @Test
    fun `when fetching characters is successful return a list of characters`() = runTest {
        val response = repository.getAllCharacters().first()
        assert(response is NetworkResult.Success)
        val data = (response as NetworkResult.Success).data
        assertEquals(data?.size, 2)
        assertEquals(data?.get(0)?.name, "Rick Sanchez")
        assertEquals(data?.get(1)?.name, "Morty Smith")
    }

    @Test
    fun `when fetching characters fail return the exception with message`() = runTest {
        apiService.isFetchCharactersSuccesfull = false

        val response = repository.getAllCharacters().first()
        assert(response is NetworkResult.Error)
        val message = (response as NetworkResult.Error).errorMessage
        assertEquals(message, GENERAL_SERVER_ERROR)
    }
}
