package com.wini.owle.ui.theme.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.wini.owle.res.Res
import com.wini.owle.res.rubik
import org.jetbrains.compose.resources.*


object Rubik: @Composable () -> FontFamily {

    @Composable
    override fun invoke(): FontFamily {
        return FontFamily(
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Black,
                style = FontStyle.Normal
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Black,
                style =FontStyle.Italic
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Bold,
                style =FontStyle.Normal
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Bold,
                style =FontStyle.Italic
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.ExtraBold,
                style =FontStyle.Normal
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.ExtraBold,
                style =FontStyle.Italic
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Normal,
                style =FontStyle.Normal
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Normal,
                style =FontStyle.Italic
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Light,
                style =FontStyle.Normal
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Light,
                style =FontStyle.Italic
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Medium,
                style =FontStyle.Normal
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.Medium,
                style =FontStyle.Italic
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.SemiBold,
                style =FontStyle.Normal
            ),
            Font(
                resource = Res.font.rubik,
                weight = FontWeight.SemiBold,
                style =FontStyle.Italic
            )
        )
    }

}