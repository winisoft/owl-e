package com.wini.owle.ui.sprite.anim

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun OwleIdling() {
    // Example of combining survey and blink:
    OwlSurvey(
        rotationDegreesFrom = -30f,
        rotationDegreesTo = 60f,
        surveyDurationMillis = 3000
    ) {
        // Wrap the actual Owl content with a blink effect:
        // You might place your eyes inside, or use multiple layers for each eye, etc.
        OwleBlinks(eyeRadius = 40.dp, basePupilRadius = 15.dp)
    }
}
