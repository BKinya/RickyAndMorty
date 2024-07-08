package com.beatrice.rickymorty.data.util

import com.beatrice.rickymorty.data.network.models.CharacterInfo
import com.beatrice.rickymorty.domain.model.Character

fun List<CharacterInfo>.toDomain(): List<Character> {
    return this.map {
        Character(
            name = it.name,
            species = it.species,
            imageUrl = it.image
        )
    }
}
