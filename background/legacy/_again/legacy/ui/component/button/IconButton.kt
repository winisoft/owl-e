package ui.component.button

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ui.component.img.Icon
import ui.component.img.Icons
import ui.theme.OwleTheme


@Composable
fun IconButton(
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var isHovered by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    Div({
        classes(
            *listOf(
                "iconButton",
                when {
                    isPressed -> "iconButtonPressed"
                    isHovered -> "iconButtonHovered"
                    else -> "iconButtonDefault"
                }
            ).toTypedArray()
        )

        attr("tabindex", "0")
        onClick { onClick() }
        onMouseEnter { isHovered = true }
        onMouseDown { isPressed = true }
        onMouseUp { isPressed = false }
        onMouseLeave {
            isHovered = false
            isPressed = false
        }
        style {
            width(size)
            height(size)
        }
    }) {
        content()
    }

}

/**
 *
 */
@Composable
fun IconButton(
    icon: Icons,
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    if (isEnabled)
        IconButtonEnabled(icon, size, onClick)
    else
        IconButtonDisabled(icon, size)
}

@Composable
fun IconButtonEnabled(
    icon: Icons,
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    onClick: () -> Unit,
) {
    var isHovered by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    Div(
        attrs = {
            classes(
                *listOf(
                    "iconButton",
                    when {
                        isPressed -> "iconButtonPressed"
                        isHovered -> "iconButtonHovered"
                        else -> "iconButtonDefault"
                    }
                ).toTypedArray()
            )
            onClick { onClick() }
            attr("tabindex", "0")
            onMouseEnter { isHovered = true }
            onMouseDown { isPressed = true }
            onMouseUp { isPressed = false }
            onMouseLeave {
                isHovered = false
                isPressed = false
            }
            style {
                width(size)
                height(size)
            }
        }
    ) {
        Div(
            attrs = {
                classes(
                    "flex",
                    "flex-col",
                    "items-center",
                    "space-y-3",
                    "w-24"
                )
            }
        ) {
            Icon(icon = icon, color = OwleTheme.Color.Background.TextOn)
        }
    }
}

@Composable
fun IconButtonDisabled(
    icon: Icons,
    size: CSSSizeValue<CSSUnit.px> = 30.px
) {
    Div(
        attrs = {
            classes("iconButton")
            attr("tabindex", "0") // Makes the button focusable
            style {
                width(size)
                height(size)
            }
        }
    ) {
        Div(
            attrs = {
                classes(
                    "flex",
                    "flex-col",
                    "items-center",
                    "space-y-3",
                    "w-24"
                )
            }
        ) {
            Icon(icon = icon, color = OwleTheme.Color.Background.TextOnInactive)
        }
    }
}
