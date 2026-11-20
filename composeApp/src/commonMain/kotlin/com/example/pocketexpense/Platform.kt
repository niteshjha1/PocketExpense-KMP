package com.example.pocketexpense

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
