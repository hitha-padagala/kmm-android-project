package com.hitha.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    val files: Map<String, String> = emptyMap(),
    val origin: String = "",
    val url: String = ""
)
