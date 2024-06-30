package com.beatrice.rickymorty.data.util

import com.beatrice.rickymorty.data.network.models.CharacterResponse
import com.beatrice.rickymorty.domain.model.Character

fun CharacterResponse.toDomain(): List<Character> {
    return emptyList()
//    val result = this.results
//    return result.map {
//        Character(
//            name = it.name,
//            species = it.species,
//            imageUrl = it.image
//        )
//    }
}
