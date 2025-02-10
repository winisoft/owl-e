package com.wini.owle.ui.theme.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.wini.owle.loadFont


object Rubik: OwleFont {

    @Composable
    override fun fontFamily(): FontFamily {
        return FontFamily(
            loadFont("Rubik-Black",
                FontWeight.Black,
                FontStyle.Normal
            ),
            loadFont(
                "Rubik-BlackItalic",
                FontWeight.Black,
                FontStyle.Italic
            ),
            loadFont(
                "Rubik-Bold",
                FontWeight.Bold,
                FontStyle.Normal
            ),
            loadFont(
                "Rubik-BoldItalic",
                FontWeight.Bold,
                FontStyle.Italic
            ),
            loadFont(
                "Rubik-ExtraBold",
                FontWeight.ExtraBold,
                FontStyle.Normal
            ),
            loadFont(
                "Rubik-ExtraBoldItalic",
                FontWeight.ExtraBold,
                FontStyle.Italic
            ),
            loadFont(
                "Rubik-Regular",
                FontWeight.Normal,
                FontStyle.Normal
            ),
            loadFont(
                "Rubik-Italic",
                FontWeight.Normal,
                FontStyle.Italic
            ),
            loadFont(
                "Rubik-Light",
                FontWeight.Light,
                FontStyle.Normal
            ),
            loadFont(
                "Rubik-LightItalic",
                FontWeight.Light,
                FontStyle.Italic
            ),
            loadFont(
                "Rubik-Medium",
                FontWeight.Medium,
                FontStyle.Normal
            ),
            loadFont(
                "Rubik-MediumItalic",
                FontWeight.Medium,
                FontStyle.Italic
            ),
            loadFont(
                "Rubik-SemiBold",
                FontWeight.SemiBold,
                FontStyle.Normal
            ),
            loadFont(
                "Rubik-SemiBoldItalic",
                FontWeight.SemiBold,
                FontStyle.Italic
            )
        )
    }
}