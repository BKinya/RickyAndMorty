package com.beatrice.rickymorty.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Origin(
    val name: String,
    val url: String
)
