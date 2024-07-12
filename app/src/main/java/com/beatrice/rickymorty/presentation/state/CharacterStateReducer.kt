package com.beatrice.rickymorty.presentation.state

class CharacterStateReducer: StateReducer<CharacterState, CharacterEvent, CharacterSideEffect> {
    override fun reduce(currentState: CharacterState, event: CharacterEvent): StateOutput<CharacterState, CharacterSideEffect?> {
       return when(currentState){
           is  CharacterState.Initial -> reduceInitial(currentState = CharacterState.Initial, event =  event)
           is  CharacterState.Loading -> reduceLoading(currentState = CharacterState.Loading, event = event)
           is CharacterState.Characters -> TODO()
           is CharacterState.Empty -> TODO()
           is CharacterState.Error -> TODO()

       }
    }

    override fun ignore(currentState: CharacterState): StateOutput<CharacterState, CharacterSideEffect?>  = StateOutput(currentState)

   private fun reduceInitial(currentState: CharacterState.Initial, event: CharacterEvent): StateOutput<CharacterState, CharacterSideEffect?> {
      return   when(event){
         is  CharacterEvent.FetchAllCharacters -> StateOutput(state = CharacterState.Loading, CharacterSideEffect.FetchCharacters )
          else -> ignore(currentState)
      }
    }

    private fun reduceLoading(currentState: CharacterState.Loading, event: CharacterEvent) : StateOutput<CharacterState, CharacterSideEffect?>{
        return when(event){
           is  CharacterEvent.FetchAllCharacters -> ignore(currentState)
            is CharacterEvent.FetchCharacterFailed -> StateOutput(state = CharacterState.Error(event.message))
            is CharacterEvent.FetchCharacterSuccessful -> StateOutput(state = CharacterState.Characters(data = event.characters))
            is CharacterEvent.NoCharacterFound -> StateOutput(state = CharacterState.Empty(event.message))
        }
    }


}
