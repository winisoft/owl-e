package com.wini.owle

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform