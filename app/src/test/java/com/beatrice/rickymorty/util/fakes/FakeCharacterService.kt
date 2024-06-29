package com.beatrice.rickymorty.util.fakes

import com.beatrice.rickymorty.data.network.CharacterService
import com.beatrice.rickymorty.data.network.models.CharacterResponse
import com.beatrice.rickymorty.util.resources.charactersJson
import com.beatrice.rickymorty.util.resources.errorFetchingCharacters
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeCharacterService : CharacterService {
    var isFetchCharactersSuccesfull = true
    override fun getAllCharacters(): Response<CharacterResponse> {
        return if (isFetchCharactersSuccesfull) {
            val characterResult = Json.decodeFromString<CharacterResponse>(charactersJson)
            val response = Response.success(
                characterResult
            )
            response
        } else {
            val response = Response
                .error<CharacterResponse>(
                    500,
                    errorFetchingCharacters.toResponseBody("application/json".toMediaTypeOrNull())
                )
            response
        }
    }
}
