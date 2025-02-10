package ui.component.img

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg
import org.jetbrains.compose.web.svg.height
import org.jetbrains.compose.web.svg.width
import ui.theme.OwleTheme


/**
 * A flexible Icon component for rendering SVG files.
 *
 * @param icon The @link{Icon} to display
 * @param color Optional CSS color to apply as a filter.
 */
//@Composable
//fun Icon(
//    icon: Icons,
//    size: CSSSizeValue<CSSUnit.px> = 24.px,
//    color: CSSColorValue = colorOwleOnBackground
//) {
//    Div(
//        attrs = {
//            classes(
//                "icon",
//                "bg-contain",
//                "bg-no-repeat",
//                "bg-center"
//            )
//            attr("aria-label", icon.contentDescription)
//            style {
//                color(color)
//                width(size)
//                height(size)
//            }
//        }
//    ) {
//        RawHtml(content = icon.svg)
//    }
//}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun Icon(
    icon: Icons,
    size: CSSSizeValue<CSSUnit.px> = 24.px,
    color: OwleTheme.ColorValue = OwleTheme.Color.Surface.TextOn
) {
    Svg(
        viewBox = icon.viewBox,
        attrs = {
            classes(color.textClass)
            width(icon.width)
            height(icon.height)
            style {
                color(color.value)
            }
        }
    ) {
        icon.paths.forEach {
            Path(
                d = it.d,
                attrs = {
                    it.attrs.forEach { atr ->
                        attr(atr.key, atr.value)
                    }
                }
            )
        }
    }
}
