package com.hitha.shared.repository

import com.hitha.shared.api.UsersApi
import com.hitha.shared.model.User

class UsersRepository(private val api: UsersApi) {

    suspend fun getUsers(): Result<List<User>> = runCatching {
        api.getUsers()
    }

    suspend fun getUser(id: Int): Result<User> = runCatching {
        api.getUser(id)
    }
}
