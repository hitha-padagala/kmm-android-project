package com.hitha.shared.api

import com.hitha.shared.model.LoginRequest
import com.hitha.shared.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthApi(private val client: HttpClient) {

    suspend fun login(email: String, password: String): LoginResponse {
        return client.post("https://reqres.in/api/login") {
            setBody(LoginRequest(email, password))
        }.body()
    }
}
