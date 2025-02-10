package com.wini.owle.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.wini.owle.ui.theme.font.JetbrainsMono
import com.wini.owle.ui.theme.font.Rubik


/**
 * Store and provides the typography
 */
internal val LocalTypography = staticCompositionLocalOf { OwleTypography }

data class OwleTypographyStyles(
    val body: TextStyle = TypographyStyles.bodyStyle(),
    val h1: TextStyle = TextStyle(
        fontFamily =  Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h2: TextStyle = TextStyle(
        fontFamily =  Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h3: TextStyle = TextStyle(
        fontFamily =  Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h4: TextStyle = TextStyle(
        fontFamily =  Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h5: TextStyle = TextStyle(
        fontFamily =  Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h6: TextStyle = TextStyle(
        fontFamily =  Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val subtitle: TextStyle = TextStyle(
        fontFamily = JetbrainsMono.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val button: TextStyle = TextStyle(
        fontFamily = Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = Rubik.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    val hyperlink: TextStyle = TypographyStyles.bodyStyle(
        textDecoration = TextDecoration.Underline
    )
) {

}

object TypographyStyles {
    fun bodyStyle(
        fontFamily: FontFamily = Rubik.fontFamily,
        fontWeight: FontWeight = FontWeight.Normal,
        fontSize: TextUnit = 16.sp,
        textDecoration: TextDecoration? = null
    ): TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,
        textDecoration = textDecoration
    )
}



