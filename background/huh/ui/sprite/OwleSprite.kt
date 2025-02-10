package com.wini.owle.ui.sprite

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview











/**
 * Draws a placeholder shape for Owl-E, just to demonstrate usage in animations.
 */
@Composable
fun OwleSprite() {


    Canvas(modifier = Modifier.size(80.dp)) {
        // Draw two squares for the eyes, slightly rotated.
        val strokeWidth = size.minDimension * 0.1f
        drawRect(
            color = Color.White,
            topLeft = center.copy(x = center.x - size.minDimension * 0.40f, y = center.y - size.minDimension * 0.30f),
            size = this.size * 0.25f,
            style = Stroke(width = strokeWidth),
            // rotation: -45 deg around center of each eye region
        )
        drawRect(
            color = Color.White,
            topLeft = center.copy(x = center.x + size.minDimension * 0.15f, y = center.y - size.minDimension * 0.30f),
            size = this.size * 0.25f,
            style = Stroke(width = strokeWidth),
            // rotation: +45 deg around center of each eye region
        )
        // Simplified line or arc for the beak, etc.
    }
}




@Preview
@Composable
fun PreviewSampleOwle() {

    OwleSprite()
}