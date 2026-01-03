package com.beatrice.rickymorty.data.repository

import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.network.util.runCatchingSuspend
import com.beatrice.rickymorty.data.util.toDomain
import com.beatrice.rickymorty.domain.model.Character


class CharacterRepository(
    private val apiService: CharacterApiService
) {
    suspend fun getAllCharacters(): Result<List<Character>> {
        return runCatchingSuspend {
            apiService.getCharacters(1).results.toDomain()
        }
    }
}
