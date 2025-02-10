package com.wini.owle.ui.img.icon.sprite.anim

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


/**
 * OwleHoots animates a "beak parameter" to simulate different mouth openings,
 * shaping the upper beak path.
 *
 * @param modifier A [Modifier] controlling the layout.
 * @param hootCycleMillis Total duration of one hoot cycle.
 */
@Composable
fun OwleHoots(
    modifier: Modifier = Modifier,
    hootCycleMillis: Int = 1200
) {
    val transition = rememberInfiniteTransition()
    val beakOpenRatio by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                // a rough musical pattern for "hoot"
                durationMillis = hootCycleMillis
                // half note (start closed, then open at ~midpoint):
                0f at 0
                0.7f at hootCycleMillis/2

                // triplet of sixteenth notes (quick open/close pulses):
                0.2f at (hootCycleMillis*0.6f).toInt()
                0.7f at (hootCycleMillis*0.65f).toInt()
                0.2f at (hootCycleMillis*0.7f).toInt()
                0.7f at (hootCycleMillis*0.75f).toInt()
                0.2f at (hootCycleMillis*0.8f).toInt()

                // dotted quarter note closing
                0.0f at hootCycleMillis
            },
            repeatMode = RepeatMode.Restart
        )
    )

    // We draw a simplified Owl shape with a top beak, whose opening is controlled by beakOpenRatio.
    //Canvas(modifier = modifier.size(120.dp)) {
        // Possibly the entire owl behind, omitted for brevity:
        // drawYourFullOwlBody(...)

        // Draw a "top beak" path that rotates or expands based on beakOpenRatio
//        drawPath(
//            path = Path().apply {
//                // For demonstration, let's do a simple arc representing the top beak.
//                // This could be replaced by your actual Bezier path if you wish.
//                val beakHeight = size.minDimension * 0.15f
//                val openingOffset = beakOpenRatio * beakHeight
//
//                moveTo(size.width * 0.4f, size.height * 0.6f)
//                quadraticTo(
//                    x1 = size.width * 0.5f,
//                    y1 = (size.height * 0.6f) - openingOffset,
//                    x2 = size.width * 0.6f,
//                    y2 = size.height * 0.6f
//                )
//            },
//            color = Color.White,
//            style = Stroke(width = 5f)
//        )
//    }
}