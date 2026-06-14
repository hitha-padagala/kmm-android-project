package com.hitha.shared.repository

import com.hitha.shared.api.AuthApi
import com.hitha.shared.model.LoginResponse

class AuthRepository(private val api: AuthApi) {

    suspend fun login(email: String, password: String): Result<LoginResponse> = runCatching {
        api.login(email, password)
    }
}
