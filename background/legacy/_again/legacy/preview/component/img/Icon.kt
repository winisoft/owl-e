package preview.component.img

import androidx.compose.runtime.Composable
import preview.PreviewItems
import preview.item.PreviewItemWithBottomLabel
import ui.component.img.Icon
import ui.component.img.Icons


@Composable
fun PreviewIcon(
    icon: Icons,
    label: String = icon::class.simpleName.toString(),
) {
    PreviewItemWithBottomLabel(
        { Icon(icon = icon) },
        label
    )
}

