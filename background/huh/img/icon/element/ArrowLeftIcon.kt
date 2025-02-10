package com.wini.owle.ui.img.icon.element

import androidx.compose.runtime.Composable
import com.wini.owle.ui.img.icon.SvgWrapper
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.svg.Path


@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun ArrowLeftIcon() {
    SvgWrapper(viewBox = "0 0 25 24", width = 25.px, height = 24.px) {
        Path(
            d = "M23.0601 12.2004H1.46008M1.46008 12.2004L12.2601 23.0004M1.46008 12.2004L12.2601 1.40039",
            attrs = {
                attr("stroke", "white")
                attr("stroke-width", "2")
                attr("stroke-linecap", "round")
                attr("stroke-linejoin", "round")
            }
        )
    }
}
