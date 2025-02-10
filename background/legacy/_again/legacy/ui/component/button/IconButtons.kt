package ui.component.button

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px
import ui.component.img.Icons
import preview.PreviewItems
import preview.item.PreviewItemWithBottomLabel


@Composable
fun AddItemIconButton(
    onClick: () -> Unit,
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    isEnabled: Boolean = true
) {
    IconButton(
        icon = Icons.AddItem,
        onClick = onClick,
        size = size,
        isEnabled = isEnabled
    )
}

@Composable
fun DataTypeIconButton(
    onClick: () -> Unit,
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    isEnabled: Boolean = true
) {

    IconButton(
        icon = Icons.DataType,
        onClick = onClick,
        size = size,
        isEnabled = isEnabled
    )
}

@Composable
fun ConfirmIconButton(
    onClick: () -> Unit,
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    isEnabled: Boolean = true
) {

    IconButton(
        icon = Icons.Check,
        onClick = onClick,
        size = size,
        isEnabled = isEnabled
    )
}

@Composable
fun CancelIconButton(
    onClick: () -> Unit,
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    isEnabled: Boolean = true
) {

    IconButton(
        icon = Icons.RemoveItem,
        onClick = onClick,
        size = size,
        isEnabled = isEnabled
    )
}

@Composable
fun EditItemIconButton(
    onClick: () -> Unit,
    size: CSSSizeValue<CSSUnit.px> = 30.px,
    isEnabled: Boolean = true
) {

    IconButton(
        icon = Icons.Pencil,
        onClick = onClick,
        size = size,
        isEnabled = isEnabled
    )
}

