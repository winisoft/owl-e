package com.wini.owle.ui.img.icon.sprite

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div


sealed class OwleParts(private val cssClass: String) {

    @Composable operator fun invoke() = Div(attrs = { classes(cssClass) }){}

    data object Beak: OwleParts("owle-beak")

    data object LeftEyeInner: OwleParts("owle-eye-inner-left")
    data object RightEyeInner: OwleParts("owle-eye-inner-right")

    data object LeftEyeOuter: OwleParts("owle-eye-outer-left")
    data object RightEyeOuter: OwleParts("owle-eye-outer-right")

    data object LeftEyePupil: OwleParts("owle-eye-pupil-left")
    data object RightEyePupil: OwleParts("owle-eye-pupil-right")

    companion object: List<OwleParts> by listOf(
        LeftEyeInner, RightEyeInner,
        LeftEyeOuter, RightEyeOuter,
        LeftEyePupil, RightEyePupil,
        Beak
    ) {
        @Composable operator fun  invoke() = forEach { it() }
    }

}