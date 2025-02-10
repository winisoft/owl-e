package com.wini.owle.ui.theme.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.wini.owle.loadFont


object JetbrainsMono: OwleFont {

    @Composable
    override fun fontFamily(): FontFamily {
        return FontFamily(
            loadFont("jetbrainsmono_regular",
                FontWeight.Normal,
                FontStyle.Normal
            ),
            loadFont(
                "jetbrainsmono_bold",
                FontWeight.Bold,
                FontStyle.Normal
            ),
            loadFont(
                "jetbrainsmono_bold_italic",
                FontWeight.Bold,
                FontStyle.Italic
            ),
            loadFont(
                "jetbrainsmono_extrabold",
                FontWeight.ExtraBold,
                FontStyle.Normal
            ),
            loadFont(
                "jetbrainsmono_extrabold_italic",
                FontWeight.ExtraBold,
                FontStyle.Italic
            ),
            loadFont(
                "jetbrainsmono_medium",
                FontWeight.Medium,
                FontStyle.Normal
            ),
            loadFont(
                "jetbrainsmono_medium_italic",
                FontWeight.Medium,
                FontStyle.Italic
            )
        )
    }
}