package ui.component.oelement.workflow

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import ui.component.layout.group.OwleColumn


@Composable
fun FoldersSummariesCard(content: @Composable () -> Unit) {
    Div(attrs = {
        classes("folders-summaries-card")
    }) {
        OwleColumn {
            content()
        }
    }
}