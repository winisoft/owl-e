package com.wini.owle

import androidx.compose.runtime.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.platform.Font as PlatformFont
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun loadFont(
    resource: String,
    weight: FontWeight,
    style: FontStyle
): Font {
    var fontData by remember { mutableStateOf<ByteArray?>(null) }
    var loadError by remember { mutableStateOf(false) }

    LaunchedEffect(resource) {
        try {
            fontData = fetchFontAsByteArray("/font/$resource.ttf")
        } catch (e: Exception) {
            console.error("Failed to load font $resource: ${e.message}")
            loadError = true
        }
    }

    if (fontData == null) {
        if (loadError) {
            throw IllegalStateException("Font $resource failed to load. Check the network or resource path.")
        }
        // Render nothing or some temporary placeholder while loading
        return PlatformFont(identity = "Loading", getData = { ByteArray(0) }, weight, style)
    }

    return PlatformFont(identity = resource, getData = { fontData!! }, weight, style)
}
