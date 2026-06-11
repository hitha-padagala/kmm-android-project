package com.hitha.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val company: Company? = null
)

@Serializable
data class Company(
    val name: String,
    val catchPhrase: String = "",
    val bs: String = ""
)
