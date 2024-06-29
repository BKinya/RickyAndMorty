package com.beatrice.rickymorty.data.network.util

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>

    @JvmInline value class Exception(val message: String) : NetworkResult<Nothing>

    @JvmInline value class Error(val errorMessage: String) : NetworkResult<Nothing>
}
