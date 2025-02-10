package com.wini.owle.ui.img.icon.element

import androidx.compose.runtime.Composable
import com.wini.owle.ui.img.icon.SvgWrapper
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.svg.Path

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun CollapseSectionIcon() {
    SvgWrapper(viewBox = "0 0 24 24", width = 24.px, height = 24.px) {
        Path(
            d = "M21 15L12 7L3 15",
            attrs = {
                attr("stroke", "white")
                attr("stroke-width", "2")
                attr("stroke-linecap", "round")
                attr("stroke-linejoin", "round")
            }
        )
    }
}
