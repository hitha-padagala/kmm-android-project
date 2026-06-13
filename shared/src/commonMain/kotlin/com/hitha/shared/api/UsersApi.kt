package com.hitha.shared.api

import com.hitha.shared.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class UsersApi {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun getUsers(): List<User> {
        return client.get("https://jsonplaceholder.typicode.com/users").body()
    }

    suspend fun getUser(id: Int): User {
        return client.get("https://jsonplaceholder.typicode.com/users/$id").body()
    }
}
