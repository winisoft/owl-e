package com.wini.owle.air.api.style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

//
///**
// * Store and provides the typography
// */
//internal val LocalTypography = staticCompositionLocalOf { LoTypography() }
//
//data class LoTypography(
//    val body: TextStyle = TypographyStyles.bodyStyle(),
//    val h1: TextStyle = TextStyle(
//        fontFamily =  Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp
//    ),
//    val h2: TextStyle = TextStyle(
//        fontFamily =  Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp
//    ),
//    val h3: TextStyle = TextStyle(
//        fontFamily =  Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp
//    ),
//    val h4: TextStyle = TextStyle(
//        fontFamily =  Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp
//    ),
//    val h5: TextStyle = TextStyle(
//        fontFamily =  Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp
//    ),
//    val h6: TextStyle = TextStyle(
//        fontFamily =  Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp
//    ),
//    val subtitle: TextStyle = TextStyle(
//        fontFamily = Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    ),
//    val button: TextStyle = TextStyle(
//        fontFamily = Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    ),
//    val caption: TextStyle = TextStyle(
//        fontFamily = Rubik.fontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp
//    ),
//    val hyperlink: TextStyle = TypographyStyles.bodyStyle(
//        textDecoration = TextDecoration.Underline
//    )
//) {
//
////    fun forElement(element: Element): TextStyle = when {
////        element.`is`("p") -> forHtmlParagraph(element as HTMLParagraphElement)
////        //element.`is`("a") -> forHtmlAnchor(element as HTMLAnchorElement)
////        else -> body
////    }
//
////    fun forHtmlParagraph(p: HTMLParagraphElement): TextStyle {
////        return body
////    }
//
////    fun forHtmlAnchor(a: HTMLAnchorElement): TextStyle {
////
////    }
//}
//
//object TypographyStyles {
//    fun bodyStyle(
//        fontFamily: FontFamily = Rubik.fontFamily,
//        fontWeight: FontWeight = FontWeight.Normal,
//        fontSize: TextUnit = 16.sp,
//        textDecoration: TextDecoration? = null
//    ): TextStyle = TextStyle(
//        fontFamily = fontFamily,
//        fontWeight = fontWeight,
//        fontSize = fontSize,
//        textDecoration = textDecoration
//    )
//}