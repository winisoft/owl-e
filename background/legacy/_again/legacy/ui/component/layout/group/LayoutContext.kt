package ui.component.layout.group

import androidx.compose.runtime.compositionLocalOf
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.px
import ui.component.layout.align.Alignment
import ui.component.layout.align.Arrangement
import ui.theme.OwleTheme


data class LayoutContext(
    val horizontalArrangement: Arrangement = Arrangement.Start,
    val verticalAlignment: Alignment.Vertical = Alignment.Vertical.Top,
    val paddingL: CSSNumeric = 0.px,
    val paddingR: CSSNumeric = 0.px,
    val paddingT: CSSNumeric = 0.px,
    val paddingB: CSSNumeric = 0.px,
    val gap: CSSNumeric = OwleTheme.Spacing.SMALL.value
)

val LocalLayoutContext = compositionLocalOf { LayoutContext() }