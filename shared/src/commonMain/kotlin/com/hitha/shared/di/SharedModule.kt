package com.hitha.shared.di

import com.hitha.shared.api.UsersApi
import com.hitha.shared.repository.UsersRepository
import org.koin.dsl.module

val sharedModule = module {
    single { UsersApi() }
    single { UsersRepository(get()) }
}
