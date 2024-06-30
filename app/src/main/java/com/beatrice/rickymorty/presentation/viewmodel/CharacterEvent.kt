package com.beatrice.rickymorty.presentation.viewmodel

/**
 * The ui is dump and passive
 *
 * The ui should just emit events
 * and receive data
 */
sealed interface CharacterEvent{
    data object FetchAllCharacters: CharacterEvent
}
