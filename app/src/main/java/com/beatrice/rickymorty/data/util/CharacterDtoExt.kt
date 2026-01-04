package com.beatrice.rickymorty.data.util

import com.beatrice.rickymorty.data.network.models.CharacterInfo
import com.beatrice.rickymorty.data.network.models.CharacterResponse
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.domain.model.CharactersResult

fun CharacterResponse.toDomain() = CharactersResult(
    characters = this.results.toDomain(),
    pages = this.info.pages,
    nextPage = this.info.next.getPage()
)
fun List<CharacterInfo>.toDomain(): List<Character> {
    return this.map {
        Character(
            name = it.name,
            species = it.species,
            imageUrl = it.image
        )
    }
}

fun String?.getPage(): Int? = this?.substringAfter("page=")?.toInt()
