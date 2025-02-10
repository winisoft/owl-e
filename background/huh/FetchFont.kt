package com.wini.owle

import kotlinx.coroutines.await
import kotlinx.browser.window
import org.khronos.webgl.Int8Array

suspend fun fetchFontAsByteArray(resourcePath: String): ByteArray {
    val response = window.fetch(resourcePath).await()
    if (!response.ok) {
        throw Exception("Failed to fetch font: ${response.statusText}")
    }
    val buffer = response.arrayBuffer().await()
    return Int8Array(buffer).unsafeCast<ByteArray>()
}