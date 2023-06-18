package com.glidotalks.android.core.api

sealed class ApiResult<out R> {

    /**
     * Declaring type parameter R as out ensures ApiResult<Base>
     * can safely be a supertype of ApiResult<Derived>
     */
    data class Success<out R>(val result: R?) : ApiResult<R>()
    data class Failure(val error: ApiError) : ApiResult<Nothing>()
}