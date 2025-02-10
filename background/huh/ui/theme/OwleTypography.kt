package com.wini.owle.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp


/**
 * Store and provides the typography
 */
internal val LocalTypography: ProvidableCompositionLocal<OwleTypography> = staticCompositionLocalOf {

    OwleTypography()

}


data class OwleTypography(
    var _h1: TextStyle,
    var _h2: TextStyle,
    var _h3: TextStyle,
    var _h4: TextStyle,
    var _h5: TextStyle,
    var _h6: TextStyle,
    var _subtitle1: TextStyle,
    var _subtitle2: TextStyle,
    var _body1: TextStyle,
    var _body2: TextStyle,
    var _button: TextStyle,
    var _caption: TextStyle,
    var _overline: TextStyle
) {

    constructor(fontFamily: FontFamily) : this(
        _h1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        _h2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        _h3 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        _h4 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        _h5 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        _h6 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        _subtitle1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        _subtitle2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        _body1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        _body2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        ),
        _button = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        _caption = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        _overline = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )

    constructor(): this(fontFamily = FontFamily())

    var h1 by mutableStateOf(_h1)
        private set

    var h2 by mutableStateOf(_h2)
        private set

    var h3 by mutableStateOf(_h3)
        private set

    var h4 by mutableStateOf(_h4)
        private set

    var h5 by mutableStateOf(_h5)
        private set

    var h6 by mutableStateOf(_h6)
        private set

    var subtitle1 by mutableStateOf(_subtitle1)
        private set

    var subtitle2 by mutableStateOf(_subtitle2)
        private set

    var body1 by mutableStateOf(_body1)
        private set

    var body2 by mutableStateOf(_body2)
        private set

    var button by mutableStateOf(_button)
        private set

    var caption by mutableStateOf(_caption)
        private set

    var overline by mutableStateOf(_overline)
        private set

    var hyperlink by mutableStateOf(_body1.copy(textDecoration = TextDecoration.Underline))
        private set

    fun updateTypographyFrom(
        other: Typography
    ) {
        h1 = other.h1
        h2 = other.h2
        h3 = other.h3
        h4 = other.h4
        h5 = other.h5
        h6 = other.h6
        subtitle1 = other.subtitle1
        subtitle2 = other.subtitle2
        body1 = other.body1
        body2 = other.body2
        button = other.button
        caption = other.caption
        overline = other.overline
    }

    fun toTypography(): Typography = Typography(
        h1 = h1,
        h2 = h2,
        h3 = h3,
        h4 = h4,
        h5 = h5,
        h6 = h6,
        subtitle1 = subtitle1,
        subtitle2 = subtitle2,
        body1 = body1,
        body2 = body2,
        button = button,
        caption = caption,
        overline = overline
    )
}