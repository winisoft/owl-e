package preview.group

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import ui.component.button.toggle.CollapseExpandToggleBtn


@Composable
fun PreviewGroupHeader(
    name: String,
    toggleExpanded: (Boolean) -> Unit,
    isExpandedInitial: Boolean = false
) {
    Div(
        attrs = {
            classes(
                "flex",
                "flex-row",
                "justify-left"
            )
        }
    ) {

        Div(
            attrs = {
                classes(
                    "text-white",
                    "font-bold",
                    "text-3xl",
                    "mb-2"
                )
            }
        ) {
            Text(name)
        }

        Div(
            attrs = {
                classes(
                    "pl-4"
                )
            }
        ) {
            CollapseExpandToggleBtn(
                onClick = toggleExpanded,
                isExpandedInitial = isExpandedInitial
            )
        }

    }

}