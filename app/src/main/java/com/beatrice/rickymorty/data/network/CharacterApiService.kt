package com.beatrice.rickymorty.data.network

import com.beatrice.rickymorty.data.network.models.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse
}
