package com.beatrice.rickymorty.di

import com.beatrice.rickymorty.BuildConfig
import com.beatrice.rickymorty.data.network.CharacterService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModules = module {
    single { createLoggingInterceptor() }
    single { createRetrofit(client = get()) }
    single { createCharacterService(retrofit = get()) }
}

fun createRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(
            Json.asConverterFactory(
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