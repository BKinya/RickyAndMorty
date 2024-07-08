package com.beatrice.rickymorty.util.fakes

import com.beatrice.rickymorty.data.network.util.GENERAL_SERVER_ERROR
import com.beatrice.rickymorty.data.network.util.NetworkResult
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import com.beatrice.rickymorty.util.resources.characters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCharacterRepository : CharacterRepository {

    var isRequestSuccessful = true
    override fun getAllCharacters(): Flow<NetworkResult<List<Character>>> = flow {
        if (isRequestSuccessful) {
            emit(NetworkResult.Success(characters))
        } else {
            emit(NetworkResult.Exception(GENERAL_SERVER_ERROR))
        }
    }
}
