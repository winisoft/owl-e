package com.wini.owle.ui.util.scrolled

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

enum class ScrollCardinalities {
    NEITHER,
    VERTICAL_ONLY,
    HORIZONTAL_ONLY,
    BOTH
}

@Composable
fun RootBackground(
    backgroundColor: Color,
    scrollCardinalities: ScrollCardinalities = ScrollCardinalities.NEITHER,
    content: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .apply {
                when (scrollCardinalities) {
                    ScrollCardinalities.NEITHER -> return
                    ScrollCardinalities.VERTICAL_ONLY -> verticalScroll(rememberScrollState())
                    ScrollCardinalities.HORIZONTAL_ONLY -> horizontalScroll(rememberScrollState())
                    ScrollCardinalities.BOTH ->  return //TODO: Replace with pointerInput
                }
            }
    ) {
        content()
    }

}