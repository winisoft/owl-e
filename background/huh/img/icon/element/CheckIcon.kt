package com.wini.owle.ui.img.icon.element

import androidx.compose.runtime.Composable
import com.wini.owle.ui.img.icon.SvgWrapper
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.svg.Path

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun CheckIcon() {
    SvgWrapper(viewBox = "0 0 24 24", width = 24.px, height = 24.px) {
        Path(
            d = "M8.9375 19L8.23209 19.7088C8.62222 20.0971 9.25278 20.0971 9.64291 19.7088L8.9375 19ZM3.70541 12.3821L2.99661 11.6767L1.58579 13.0943L2.29459 13.7997L3.70541 12.3821ZM21.2946 5.2912L8.23209 18.2912L9.64291 19.7088L22.7054 6.7088L21.2946 5.2912ZM9.64291 18.2912L3.70541 12.3821L2.29459 13.7997L8.23209 19.7088L9.64291 18.2912Z",
            attrs = { attr("fill", "white") }
        )
    }
}
