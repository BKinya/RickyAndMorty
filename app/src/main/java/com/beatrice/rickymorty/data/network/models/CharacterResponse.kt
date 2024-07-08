package com.beatrice.rickymorty.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val info: Info,
    @SerialName("results")
    val characterInfos: List<CharacterInfo>
)
