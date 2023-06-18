package com.glidotalks.android.core.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

@Suppress("UNCHECKED_CAST")
open class ApiResponseWrapper(moshi: Moshi) {

    private val errorJsonAdapter: JsonAdapter<ApiError> = moshi.adapter(ApiError::class.java)

    suspend fun <R> safeApiCall(
        call: suspend () -> Response<R>
    ): ApiResult<R> {
        return try {
            val response = call.invoke()
            handleResponse(response)
        } catch (throwable: Throwable) {
            handleThrowable(throwable)
        }
    }

    private fun <R> handleResponse(response: Response<R>) = when {
        response.isSuccessful && response.code() == HttpURLConnection.HTTP_NO_CONTENT -> {
            ApiResult.Success(Unit as R)
        }
        response.isSuccessful -> {
            ApiResult.Success(response.body())
        }
        else -> {
            handleHttpFailures(response)
        }
    }

    private fun <R> handleThrowable(
        throwable: Throwable
    ): ApiResult<R> {
        return when (throwable) {
            is SocketTimeoutException -> ApiResult.Failure(ApiError())
            is IOException -> ApiResult.Failure(ApiError())
            is HttpException -> ApiResult.Failure(ApiError())
            else -> ApiResult.Failure(ApiError())
        }
    }

    private fun <R> handleHttpFailures(
        response: Response<R>
    ): ApiResult.Failure {
        return when (response.code()) {
            HttpURLConnection.HTTP_NOT_FOUND -> {
                ApiResult.Failure(ApiError())
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                ApiResult.Failure(ApiError())
            }
            else -> ApiResult.Failure(deserializeErrorResponse(response))
        }
    }

    private fun <R> deserializeErrorResponse(
        response: Response<R>
    ): ApiError {
        val errorResponse = errorJsonAdapter.fromJson(response.errorBody()?.charStream().toString())
        return errorResponse ?: ApiError()
    }

}