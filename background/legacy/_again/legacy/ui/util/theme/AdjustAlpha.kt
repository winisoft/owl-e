package ui.util.theme

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgba


/**
 * Adjusts the alpha component of a given color.
 *
 * @param color The original color in hexadecimal format (0xAARRGGBB).
 * @param alphaMultiplier The multiplier to apply to the original alpha component (0.0 to 1.0).
 * @return A CSSColorValue with the adjusted alpha.
 */
fun adjustAlpha(color: Long, alphaMultiplier: Double): CSSColorValue {
    require(alphaMultiplier in 0.0..1.0) { "Alpha multiplier must be between 0.0 and 1.0" }

    val originalAlpha = (color shr 24 and 0xFF)
    val newAlpha = (originalAlpha * alphaMultiplier).toInt()
    val red = (color shr 16 and 0xFF).toInt()
    val green = (color shr 8 and 0xFF).toInt()
    val blue = (color and 0xFF).toInt()

    return rgba(red, green, blue, newAlpha / 255.0)
}