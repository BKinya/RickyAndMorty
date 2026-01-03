package com.beatrice.rickymorty.di

import com.beatrice.rickymorty.BuildConfig
import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.state.CharacterEvent
import com.beatrice.rickymorty.presentation.state.CharacterReducer
import com.beatrice.rickymorty.presentation.state.CharacterSideEffect
import com.beatrice.rickymorty.presentation.state.CharacterPaginationState
import com.beatrice.rickymorty.presentation.state.DefaultStateMachine
import com.beatrice.rickymorty.presentation.state.StateMachine
import com.beatrice.rickymorty.presentation.state.StateReducer
import com.beatrice.rickymorty.presentation.viewmodel.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModules = module {
    single { createLoggingInterceptor() }
    single { createRetrofit(client = get()) }
    single { createCharacterService(retrofit = get()) }

    factory<CharacterRepository> { CharacterRepository(apiService = get()) }

    single { Dispatchers.IO }

    single<StateReducer<CharacterPaginationState, CharacterEvent, CharacterSideEffect>>(named("characterReducer")) { CharacterReducer() }
    single<StateMachine<CharacterPaginationState, CharacterEvent, CharacterSideEffect>>(named("characterStateMachine")) {
        DefaultStateMachine(
            context = Dispatchers.IO,
            initialState = CharacterPaginationState.Default,
            reducer = get(named("characterReducer"))
        )
    }

    viewModel {
        CharacterViewModel(
            dispatcher = get(),
            characterRepository = get(),
            stateMachine = get(named("characterStateMachine"))
        )
    }
}

private val jsonConverter = Json {
    ignoreUnknownKeys = true
}

fun createRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(
            jsonConverter.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .build()
}

fun createLoggingInterceptor(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
}

fun createCharacterService(retrofit: Retrofit): CharacterApiService = retrofit.create(CharacterApiService::class.java)
