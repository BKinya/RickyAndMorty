package com.beatrice.rickymorty.data.repository

import com.beatrice.rickymorty.data.network.CharacterService
import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.data.network.util.safeApiRequest
import com.beatrice.rickymorty.data.util.toDomain
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl(
    private val apiService: CharacterService
) : CharacterRepository {
    override fun getAllCharacters(): Flow<NetworkResult<List<Character>?>> = flow {
        /**
         * Get the network result with all the unwanted things
         */
        val response = safeApiRequest { apiService.getAllCharacters() }

        /**
         * Convert the result to the suitable data types
         */
        val result = when (response) {
            is NetworkResult.Success -> {
                val characters = response.data?.toDomain()
                NetworkResult.Success(data = characters)
            }
            is NetworkResult.Error -> NetworkResult.Error(response.errorMessage)
            is NetworkResult.Exception -> NetworkResult.Exception(response.message)
        }
        emit(result)
    }
}
