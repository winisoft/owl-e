package com.wini.owle.ui.sprite.anim

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * OwleBlinks draws Owl-e's eye(s) and animates the "pupil" to simulate a blink.
 *
 * @param modifier A [Modifier] to size and arrange this composable on screen.
 * @param blinkDurationMillis Duration in milliseconds of a single blink cycle.
 * @param eyeRadius The radius used to draw the owl's eye shape.
 * @param basePupilRadius The pupil radius when fully "open."
 */
@Composable
fun OwleBlinks(
    modifier: Modifier = Modifier,
    blinkDurationMillis: Int = 400,
    eyeRadius: Dp = 30.dp,
    basePupilRadius: Dp = 12.dp
) {
    // A repeating transition that goes from 1f -> 0f -> 1f to “expand” and “shrink.”
    val infiniteTransition = rememberInfiniteTransition()
    val pupilScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(blinkDurationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.size(eyeRadius * 2)) {
        // Draw the outer eye
        drawCircle(
            color = Color.White,
            radius = eyeRadius.toPx()
        )
        // Draw the pupil, scaled in radius
        drawCircle(
            color = Color.Black,
            radius = basePupilRadius.toPx() * pupilScale
        )
    }
}