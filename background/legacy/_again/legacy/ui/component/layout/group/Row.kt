package ui.component.layout.group

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import ui.component.layout.align.Alignment
import ui.component.layout.align.Arrangement
import ui.component.style.CssProperty


@Composable
fun OwleRow(
    horizontalArrangement: Arrangement = LocalLayoutContext.current.horizontalArrangement,
    verticalAlignment: Alignment.Vertical = LocalLayoutContext.current.verticalAlignment,
    paddingL: CSSNumeric = LocalLayoutContext.current.paddingL,
    paddingR: CSSNumeric = LocalLayoutContext.current.paddingR,
    paddingT: CSSNumeric = LocalLayoutContext.current.paddingT,
    paddingB: CSSNumeric = LocalLayoutContext.current.paddingB,
    gap: CSSNumeric = LocalLayoutContext.current.gap,
    width: CSSNumeric? = null,
    height: CSSNumeric? = null,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutContext provides LocalLayoutContext.current.copy(
            horizontalArrangement,
            verticalAlignment,
            paddingL,
            paddingR,
            paddingT,
            paddingB,
            gap
        )
    ) {
        Div(attrs = {
            classes("owle-row")
            style {
                CssProperty.Display.Flex(this)
                CssProperty.FlexDirection.Row(this)
                CssProperty.JustifyContent(horizontalArrangement.cssValue)(this)
                CssProperty.AlignItems(verticalAlignment.cssValue)(this)
                CssProperty.Padding(paddingL, paddingR, paddingT, paddingB)(this)
                property("gap", gap.toString())
                if (width != null)
                    width(width)
                if (height != null)
                    height(height)
            }
        }) {
            content()
        }
    }
}


