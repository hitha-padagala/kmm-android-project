package com.hitha.android.di

import android.app.Application
import com.hitha.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun Application.initKoin() {
    startKoin {
        androidContext(this@initKoin)
        modules(sharedModule)
    }
}
