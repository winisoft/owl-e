package com.wini.owle.ui.theme.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.wini.owle.res.Res
import com.wini.owle.res.jetbrainsmono
import com.wini.owle.res.rubik
import org.jetbrains.compose.resources.*



object JetbrainsMono: @Composable () -> FontFamily {

    @Composable
    override fun invoke(): FontFamily {
        return FontFamily(
            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            ),
            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.Normal,
                style = FontStyle.Italic
            ),

            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            ),
            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.Bold,
                style = FontStyle.Italic
            ),

            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.ExtraBold,
                style = FontStyle.Normal
            ),
            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.ExtraBold,
                style = FontStyle.Italic
            ),

            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.Medium,
                style = FontStyle.Normal
            ),
            Font(
                resource = Res.font.jetbrainsmono,
                weight = FontWeight.Medium,
                style = FontStyle.Italic
            )
        )
    }
}