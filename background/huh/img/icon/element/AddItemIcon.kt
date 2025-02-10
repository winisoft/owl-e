package com.wini.owle.ui.img.icon.element

import androidx.compose.runtime.Composable
import com.wini.owle.ui.img.icon.SvgWrapper
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.svg.Path


@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun AddItemIcon() {
    SvgWrapper(viewBox = "0 0 20 20", width = 20.px, height = 20.px) {
        Path(
            d = "M11.9172 0H7.94483V7.94483H0V11.9172H7.94483V19.8621H11.9172V11.9172H19.8621V7.94483H11.9172V0Z",
            attrs = { attr("fill", "white") }
        )
    }
}
