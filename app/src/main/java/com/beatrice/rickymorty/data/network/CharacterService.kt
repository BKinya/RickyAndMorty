package com.beatrice.rickymorty.data.network

import com.beatrice.rickymorty.data.network.models.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterService {

    // TODO 1: Pagination ???
    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterResponse>
}
