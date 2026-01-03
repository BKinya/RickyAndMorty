package com.beatrice.rickymorty.data.network.util

import kotlinx.coroutines.CancellationException

inline fun <T> runCatchingSuspend(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Exception) {
        Result.failure(e)
    }
}
