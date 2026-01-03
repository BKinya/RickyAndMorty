package com.beatrice.rickymorty.domain.model

data class CharactersResult(
    val pages: Int,
    val nextPage: Int?,
    val characters: List<Character>
)
