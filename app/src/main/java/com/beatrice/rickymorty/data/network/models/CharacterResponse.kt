package com.beatrice.rickymorty.data.network.models

data class CharacterResponse(
    val info: Info,
    val results: List<Result>
)
