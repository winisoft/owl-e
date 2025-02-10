package com.wini.owle

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.wini.owle.preview.PreviewIcons
import com.wini.owle.ui.theme.OwleTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
fun main() = application {

    GlobalScope.launch {
        startServerSafely()
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Owl-E",
    ) {
        OwleTheme {
            PreviewIcons()
        }

    }

}