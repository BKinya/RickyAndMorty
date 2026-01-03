package com.beatrice.rickymorty.data.repository

import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.util.runCatchingSuspend
import com.beatrice.rickymorty.data.util.toDomain
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.domain.model.CharactersResult


class CharacterRepository(
    private val apiService: CharacterApiService
) {
    suspend fun getAllCharacters(page: Int): Result<CharactersResult> {
        return runCatchingSuspend {
            apiService.getCharacters(page).toDomain()
        }
    }
}
