package com.misw.vinilos.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse (
    val statusCode: Int,
    val message: String,
)
