package com.glidotalks.android.core.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiError(
    @field:Json(name = "status") val code: Int? = null,
    @field:Json(name = "message") val message: String? = null
)