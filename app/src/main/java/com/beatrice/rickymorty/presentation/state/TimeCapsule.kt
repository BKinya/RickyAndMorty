package com.beatrice.rickymorty.presentation.state

interface TimeCapsule<S> {
    fun addState(state: S)
    fun getStates(): List<S>
}

class CharacterTimeTravelCapsule<S> : TimeCapsule<S> {
    private val statesList = mutableListOf<S>()
    override fun addState(state: S) {
        statesList.add(state)
    }

    override fun getStates(): List<S> {
        return statesList
    }
}
