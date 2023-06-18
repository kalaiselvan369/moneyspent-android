package com.glidotalks.moneyspent.github.feature.user.model

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?
)