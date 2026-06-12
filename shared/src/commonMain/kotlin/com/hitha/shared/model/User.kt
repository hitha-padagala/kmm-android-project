package com.hitha.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address? = null,
    val phone: String,
    val website: String,
    val company: Company? = null
)

@Serializable
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo? = null
)

@Serializable
data class Geo(
    val lat: String,
    val lng: String
)

@Serializable
data class Company(
    val name: String,
    val catchPhrase: String = "",
    val bs: String = ""
)
