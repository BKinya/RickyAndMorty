package com.beatrice.rickymorty.data.network.util

import logcat.asLog
import logcat.logcat
import okio.IOException
import retrofit2.Response

suspend fun <T> safeApiRequest(
    block:  suspend () -> Response<T>
): NetworkResult<T?> {
    val result = try {
        val response = block()
        if (response.isSuccessful){
            if (response.body() != null) {
                NetworkResult.Success(data = response.body())
            } else {
                logcat(SERVER_ERROR_TAG) { "Null result" }
                NetworkResult.Error(GENERAL_SERVER_ERROR)
            }
        }else{
            NetworkResult.Error(GENERAL_SERVER_ERROR)
        }


    } catch (e: IOException) {
        println("Exception 1 is ${e.message}")
        logcat(SERVER_ERROR_TAG) { e.asLog() }
        NetworkResult.Exception(NO_INTERNET)
    } catch (exception: Exception) {
        println("Exception 2 is ${exception.message}")
        logcat(SERVER_ERROR_TAG) { exception.asLog() }
        NetworkResult.Exception(GENERAL_SERVER_ERROR)
    }

    return result
}
