package ui.component.button

import androidx.compose.runtime.Composable
import ui.component.img.Icons


@Composable
fun FloatingActionButton(
    icon: Icons,
    onToggle: () -> Unit
) {

    IconButton(icon = icon, onClick = onToggle)

}