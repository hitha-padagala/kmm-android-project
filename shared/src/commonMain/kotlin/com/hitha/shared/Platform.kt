package com.hitha.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
