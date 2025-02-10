package ui.util.theme

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgba

fun hexToRgba(hex: Long): CSSColorValue {
    val alpha = (hex shr 24 and 0xFF) / 255.0
    val red = (hex shr 16 and 0xFF).toInt()
    val green = (hex shr 8 and 0xFF).toInt()
    val blue = (hex and 0xFF).toInt()
    return rgba(red, green, blue, alpha)
}