package ui.component.button.toggle

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px
import ui.component.button.IconButton
import ui.component.img.Icons


@Composable
fun ToggleIconButton(
    toggleActiveIcon: Icons,
    toggleInactiveIcon: Icons,
    onClick: (Boolean) -> Unit,
    initialToggledState: Boolean = false,
    size: CSSSizeValue<CSSUnit.px> = 30.px
) {

    var isToggled by remember { mutableStateOf(initialToggledState) }

    IconButton(
        icon = if (isToggled) toggleActiveIcon else toggleInactiveIcon,
        onClick = {
            isToggled = !isToggled
            onClick(isToggled)
        },
        size = size
    )

}

