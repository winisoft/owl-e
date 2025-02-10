package com.wini.owle.ui.sprite.anim

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.wini.owle.ui.sprite.OwleSprite


/**
 * OwlSurvey rotates Owl-E’s entire sprite around its center to simulate casual head turns.
 *
 * @param modifier A [Modifier] controlling size and arrangement.
 * @param rotationDegreesFrom The starting angle.
 * @param rotationDegreesTo The final angle.
 * @param surveyDurationMillis The time it takes to go from start to end rotation (and back).
 * @param content A composable function that draws the Owl-E sprite at its default orientation.
 */
@Composable
fun OwlSurvey(
    modifier: Modifier = Modifier,
    rotationDegreesFrom: Float = -30f,
    rotationDegreesTo: Float = 30f,
    surveyDurationMillis: Int = 2000,
    content: @Composable () -> Unit
) {
    Surface {
        val transition = rememberInfiniteTransition()
        val angle by transition.animateFloat(
            initialValue = rotationDegreesFrom,
            targetValue = rotationDegreesTo,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = surveyDurationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        // Place the Owl, then apply rotation
        Box(
            modifier = modifier
                .size(100.dp)            // arbitrary size for demonstration
                .rotate(angle)
        ) {
            content()
        }
    }
}


@Composable
fun MySurveyingOwl() {
    OwlSurvey(
        rotationDegreesFrom = -45f,
        rotationDegreesTo = 60f,
        surveyDurationMillis = 3000
    ) {
        OwleSprite()
    }
}