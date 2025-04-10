package com.wini.owle


inline fun <T> T?.ifNullExit(block: () -> Nothing): T {
    return this ?: block()
}