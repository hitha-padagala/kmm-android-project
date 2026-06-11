package com.hitha.android

import android.app.Application
import com.hitha.android.di.initKoin

class KmmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
