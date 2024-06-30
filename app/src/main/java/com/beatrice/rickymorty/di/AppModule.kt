package com.beatrice.rickymorty.di

import com.beatrice.rickymorty.BuildConfig
import com.beatrice.rickymorty.data.network.CharacterService
import com.beatrice.rickymorty.data.repository.CharacterRepositoryImpl
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import com.beatrice.rickymorty.presentation.viewmodel.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModules = module {
    single { createLoggingInterceptor() }
    single { createRetrofit(client = get()) }
    single { createCharacterService(retrofit = get()) }

    factory<CharacterRepository> { CharacterRepositoryImpl(apiService = get()) }

    factory { Dispatchers.IO }
    viewModel {
        CharacterViewModel(
            dispatcher = get(),
            characterRepository = get()
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

fun createCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(CharacterService::class.java)
