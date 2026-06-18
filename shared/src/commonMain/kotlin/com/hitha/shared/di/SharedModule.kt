package com.hitha.shared.di

import com.hitha.shared.api.AuthApi
import com.hitha.shared.api.UploadApi
import com.hitha.shared.api.UsersApi
import com.hitha.shared.repository.AuthRepository
import com.hitha.shared.repository.UsersRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val sharedModule = module {
    single {
        HttpClient {
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
    }
    single { AuthApi(get()) }
    single { AuthRepository(get()) }
    single { UsersApi(get()) }
    single { UsersRepository(get()) }
    single { UploadApi(get()) }
}
