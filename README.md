# Ricky and Morty
An Android app displaying Ricky and Morty characters.

## Motivation
To implement state machine into an Android app. 

Every user-facing is a state machine. A finite state machine is a device that can be in exactly one 
of the finite states at a given time. For example, a light switch. 

<img src="https://github.com/BKinya/RickyAndMorty/blob/main/media/state_machine.png" alt="Light switch as a state machine">

A state machine transitions from one state to another depending on the current state and the input 
given. It has three components:
- State: Status of the state machine e.g. whether the light switch is on or off.
- Events: Inputs to the state machine like pressing a button
- Transitions: They move the state machine from one state to another when it receives an event. 


## Implementation
Define states, events, and side effects using Kotlin's `sealed interface`:
```kotlin
sealed interface CharacterPaginationState {
    data object Default : CharacterPaginationState
    data object InitialLoading : CharacterPaginationState
    data class Content(
        val characters: List<Character>,
        ...
    ) : CharacterPaginationState

    data class InitialError(val message: String) : CharacterPaginationState
}


sealed interface CharacterEvent {
    data object OnInitialFetchCharacters : CharacterEvent
    data class OnInitialFetchCharactersSuccess(val characters: List<Character>, val nextPage: Int?) : CharacterEvent
    data class OnInitialFetchCharactersFailure(val message: String) : CharacterEvent
    ...
}

sealed interface CharacterSideEffect {
    data object InitialFetchCharacters : CharacterSideEffect
    data class LoadMoreCharacters(val page: Int?) : CharacterSideEffect
}
```

Create a state machine interface: 
```kotlin
interface StateMachine<State, Event, SideEffect> {

    val state: StateFlow<State>

    val sideEffect: Flow<SideEffect>
    
    suspend fun accept(event: Event)
}
```
- The `state` variable holds the current state of the FSM.
- The `sideEffect` variable holds side effect that could be produced by an event
- The `accept(event: Event)` method receives the event sent to the FSM. The state machine will 
process the event and update the value of `state`. 

Then inject the state machine the `ViewModel` class: 

```kotlin
class CharacterViewModel(
    val stateMachine: StateMachine<CharacterState, CharacterEvent, CharacterSideEffect>,
    // Other dependencies
) : ViewModel() {
    // The rest of the code
}

```


## Resources
- [Discover Event-Driven Architecture for Android](https://proandroiddev.com/discovering-event-driven-architecture-for-android-717e6332065e)
