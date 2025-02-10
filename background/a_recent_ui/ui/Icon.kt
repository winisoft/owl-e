package com.wini.owle.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.wini.owle.ui.util.Res
import com.wini.owle.ui.theme.OwleTheme


@Composable
fun Icon() {
    painterResource(Res.Drawable.alert)
}

@Composable
@Preview
fun PreviewIcon() {
    OwleTheme {
        Icon()
    }

}