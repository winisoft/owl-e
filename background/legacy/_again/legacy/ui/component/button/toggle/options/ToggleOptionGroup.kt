package ui.component.button.toggle.options

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import ui.component.img.Icon
import ui.component.img.Icons


@Composable
fun <T : Enum<T>> ToggleOptionGroup(
    options: Map<T, Icons>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    isDisplayed: Boolean
) {
    if (!isDisplayed) return

    Div({
        classes("flex", "rounded-lg", "shadow-lg", "p-1", "bg-surface")
    }) {
        options.entries.forEachIndexed { index, entry ->
            val (option, icon) = entry
            val isActive = option == selectedOption
            val borderRadius = when (index) {
                0 -> "rounded-tl-lg rounded-bl-lg" // First button.
                options.size - 1 -> "rounded-tr-lg rounded-br-lg" // Last button.
                else -> "rounded-none" // Middle buttons.
            }

            Button({
                onClick { onOptionSelected(option) }
                classes(
                    "toggle-button",
                    if (isActive) "bg-primary text-white" else "bg-secondary text-gray-500",
                    borderRadius
                )
                title(option.name.replace("_", " ")
                    .replaceFirstChar {
                        if (it.isLowerCase())
                            it.titlecase()
                        else
                            it.toString()
                    })
            }) {
                Icon(icon)
            }
        }
    }
}
