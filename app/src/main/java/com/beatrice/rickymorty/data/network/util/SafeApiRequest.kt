package com.beatrice.rickymorty.data.network.util

import logcat.asLog
import logcat.logcat
import okio.IOException
import retrofit2.Response

fun <T> safeApiRequest(
    block: () -> Response<T>
): NetworkResult<T?> {
    val result = try {
        val response = block()
        if (response.isSuccessful) {
            if (response.body() != null) {
                NetworkResult.Success(data = response.body())
            } else {
                logcat(SERVER_ERROR_TAG) { "Null result" }
                NetworkResult.Error(GENERAL_ERROR)
            }
        } else {
            val statusCode = response.code()
            if (statusCode == 404) {
                NetworkResult.Error(NOT_FOUND)
            } else {
                NetworkResult.Error(GENERAL_ERROR)
            }
        }
    } catch (e: IOException) {
        logcat(SERVER_ERROR_TAG) { e.asLog() }
        NetworkResult.Exception(NO_INTERNET)
    } catch (e: Exception) {
        logcat(SERVER_ERROR_TAG) { e.asLog() }
        NetworkResult.Exception(GENERAL_ERROR)
    }

    return result
}
