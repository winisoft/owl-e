package ui.component.button.toggle

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px
import ui.component.img.Icons


@Composable
fun CollapseExpandToggleBtn(
    onClick: (Boolean) -> Unit,
    isExpandedInitial: Boolean,
    size: CSSSizeValue<CSSUnit.px> = 30.px
) {

    ToggleIconButton(
        toggleActiveIcon = Icons.CollapseSection,
        toggleInactiveIcon = Icons.ExpandSection,
        onClick = onClick,
        size = size,
        initialToggledState = isExpandedInitial
    )

}


@Composable
fun FilterToggleBtn(
    onClick: (Boolean) -> Unit,
    size: CSSSizeValue<CSSUnit.px> = 30.px
) {

    ToggleIconButton(
        toggleActiveIcon = Icons.Filter,
        toggleInactiveIcon = Icons.FilterOff,
        onClick = onClick,
        size = size
    )

}
