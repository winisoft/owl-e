package ui.component.img.sprite

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

@Composable
fun OwleSprite(isPeering: Boolean, isTilting: Boolean) {
    Div(attrs = {
        classes("owle-sprite")
        if (isTilting) {
            style { property("animation-name", "tilt") }
        }
    }) {
        OwleParts.Beak()
        OwleParts.LeftEyeInner()
        OwleParts.RightEyeInner()
        OwleParts.LeftEyeOuter()
        OwleParts.RightEyeOuter()

        // Apply the pupil animations for peering
        Div(attrs = {
            classes("owle-eye-pupil-left")
            if (isPeering) {
                style { property("animation-name", "peer-left") }
            }
        })
        Div(attrs = {
            classes("owle-eye-pupil-right")
            if (isPeering) {
                style { property("animation-name", "peer-right") }
            }
        })
    }
}


sealed class OwleParts(private val cssClass: String) {
    @Composable operator fun invoke() = Div(attrs = { classes(cssClass) }){}
    data object Beak: OwleParts("owle-beak")
    data object LeftEyeInner: OwleParts("owle-eye-inner-left")
    data object RightEyeInner: OwleParts("owle-eye-inner-right")
    data object LeftEyeOuter: OwleParts("owle-eye-outer-left")
    data object RightEyeOuter: OwleParts("owle-eye-outer-right")
    data object LeftEyePupil: OwleParts("owle-eye-pupil-left")
    data object RightEyePupil: OwleParts("owle-eye-pupil-right")
}