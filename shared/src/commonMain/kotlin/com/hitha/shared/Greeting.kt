package com.hitha.shared

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello from ${platform.name}!"
    }
}
