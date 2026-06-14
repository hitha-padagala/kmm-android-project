package com.hitha.shared.api

import com.hitha.shared.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UsersApi(private val client: HttpClient) {

    suspend fun getUsers(): List<User> {
        return client.get("https://jsonplaceholder.typicode.com/users").body()
    }

    suspend fun getUser(id: Int): User {
        return client.get("https://jsonplaceholder.typicode.com/users/$id").body()
    }
}
