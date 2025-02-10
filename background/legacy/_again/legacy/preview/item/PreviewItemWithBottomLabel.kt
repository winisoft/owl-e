package preview.item

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

@Composable
fun PreviewItemWithBottomLabel(
    block: @Composable () -> Unit,
    name: String
) {
    Div(
        attrs = {
            classes("flex", "flex-col", "items-center", "space-y-2", "w-24") // Adjust width as needed
        }
    ) {
        // Icon at the top
        Div(
            attrs = {
                classes("p-4", "p-4") // Proportions based on your example
            }
        ) {
            block()
        }

        PreviewItemBottomLabel(name)

    }
}