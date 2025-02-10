package com.wini.owle.ui.theme.font

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight


object JetbrainsMono {

    fun res(fileWithoutExt: String): String = "font/jetbrainsmono/$fileWithoutExt.ttf"

    val fontFamily = FontFamily(
        Font(
            "JetBrains Mono",
            res("jetbrainsmono_regular"),
            FontWeight.Normal,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            res("jetbrainsmono_italic"),
            FontWeight.Normal,
            FontStyle.Italic
        ),

        Font(
            "JetBrains Mono",
            res("jetbrainsmono_bold"),
            FontWeight.Bold,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            res("jetbrainsmono_bold_italic"),
            FontWeight.Bold,
            FontStyle.Italic
        ),

        Font(
            "JetBrains Mono",
            res("jetbrainsmono_extrabold"),
            FontWeight.ExtraBold,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            res("jetbrainsmono_extrabold_italic"),
            FontWeight.ExtraBold,
            FontStyle.Italic
        ),

        Font(
            "JetBrains Mono",
            res("jetbrainsmono_medium"),
            FontWeight.Medium,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            res("jetbrainsmono_medium_italic"),
            FontWeight.Medium,
            FontStyle.Italic
        )
    )
}