package ui

import androidx.compose.runtime.*
import com.wini.owle.MockModels
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ui.action.WorkflowFolderActions
import ui.action.WorkflowSummaryActions
import ui.component.button.LeftPanelExpandedBtn
import ui.component.img.Icon
import ui.component.img.Icons
import ui.component.items.WorkflowSummariesGrid
import ui.component.layout.group.OwleColumn
import ui.component.layout.group.OwleRow
import ui.component.layout.panel.LeftPanel
import ui.theme.OwleTheme


@Composable
fun ApplicationView(
    owleState: OwleState = OwleState(),
    workflowFolderActions: (WorkflowFolderActions) -> Unit = {},
    workflowSummaryActions: (WorkflowSummaryActions) -> Unit = {}
) {
    var isPanelExpanded by remember { mutableStateOf(true) }

    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            width(100.vw)
            height(100.vh)
        }
    }) {
        OwleRow {
            if (isPanelExpanded) {
                LeftPanel {
                    OwleColumn {
                        Icon(
                            icon = Icons.OwleBanner,
                            color = OwleTheme.Color.Background.TextOn
                        )
                        WorkflowFolder(
                            0,
                            owleState.folderRoot,
                            workflowFolderActions
                        )
                    }
                }
            }
            LeftPanelExpandedBtn(
                isPanelExpanded = isPanelExpanded,
                onToggle = { isPanelExpanded = !isPanelExpanded }
            )
            Div({
                classes("right-panel")
            }) {
                WorkflowSummariesGrid(
                    summaries = MockModels.workflowSummaries(),
                    action = workflowSummaryActions
                )
            }
        }
    }

}

