package com.beatrice.rickymorty.domain.repository

import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<NetworkResult<List<Character>>>
}
