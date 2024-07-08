package com.beatrice.rickymorty.data.network.util

import logcat.asLog
import logcat.logcat
import okio.IOException
import retrofit2.Response


inline fun <T> safeApiRequest(
  block:  () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = block()
        if (response.isSuccessful && response.body() != null){
            val data = response.body()!!
            NetworkResult.Success(data = data)
        }else{
            logcat(SERVER_ERROR_TAG){"Something Went Wrong. Error is => ${response.errorBody()}"}
            NetworkResult.Error(GENERAL_SERVER_ERROR)
        }
    } catch (e: IOException) {
        logcat(SERVER_ERROR_TAG) { e.asLog() }
        NetworkResult.Exception(NO_INTERNET)
    } catch (exception: Exception) {
        logcat(SERVER_ERROR_TAG) { exception.asLog() }
        NetworkResult.Exception(GENERAL_SERVER_ERROR)
    }
}
