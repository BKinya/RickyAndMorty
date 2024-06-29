package com.beatrice.rickymorty.util.resources

import com.beatrice.rickymorty.domain.model.Character

val characters = listOf(
    Character(
        name = "Rick Sanchez",
        species = "Human",
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    ),
    Character(
        name = "Morty Smith",
        species = "Human",
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
    )
)
