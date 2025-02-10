package com.wini.owle.ui.theme

import androidx.compose.material.Typography
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
//internal val LocalTypography = staticCompositionLocalOf {
//    val family = Rubik()
//    OwleTypographyStyles(
//        family = family,
//        body = TypographyStyles.bodyStyle(family),
//        h1 = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 24.sp
//        ),
//        h2 = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 24.sp
//        ),
//        h3 = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 24.sp
//        ),
//        h4 = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 24.sp
//        ),
//        h5 = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 24.sp
//        ),
//        h6 = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 12.sp
//        ),
//        subtitle = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp
//        ),
//        button = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp
//        ),
//        caption = TextStyle(
//            fontFamily = family,
//            fontWeight = FontWeight.Normal,
//            fontSize = 12.sp
//        ),
//        hyperlink = TypographyStyles.bodyStyle(
//            fontFamily = family,
//            textDecoration = TextDecoration.Underline
//        )
//    ).run {
//        Typography(
//            defaultFontFamily = family,
//            h1,
//            h2,
//            h3,
//            h4,
//            h5,
//            h6,
//            subtitle1 = subtitle,
//            subtitle2 = subtitle,
//            body1 = TextStyle(
//                fontSize = 14.sp,
//                lineHeight = 20.sp,
//                fontFamily = family
//            ),
//            body2 = TextStyle(
//                fontSize = 14.sp,
//                lineHeight = 20.sp,
//                fontFamily = family
//            ),
//            button = button,
//            caption = caption,
//            overline = caption
//        )
//    }
//}

object TypographyStyles {
    fun bodyStyle(
        fontFamily: FontFamily,
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





data class OwleTypographyStyles(
    val family: FontFamily,
    val body: TextStyle = TypographyStyles.bodyStyle(family),
    val h1: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h2: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h3: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h4: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h5: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val h6: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val subtitle: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val button: TextStyle = TextStyle(
        fontFamily =family,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    val hyperlink: TextStyle = TypographyStyles.bodyStyle(
        fontFamily = family,
        textDecoration = TextDecoration.Underline
    )
) {

}



