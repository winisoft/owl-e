package ui.component.oelement.workflow

import androidx.compose.runtime.Composable
import com.wini.owle.models.Workflow
import org.jetbrains.compose.web.css.minHeight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import preview.component.button.ControlPanel
import ui.component.button.IconButton
import ui.component.img.Icons
import ui.component.row.OElementPanel
import ui.component.text.InfoDescription
import ui.component.text.OElementMaximizedName
import ui.theme.OwleTheme.Text
import ui.component.text.OwleText
import ui.theme.OwleTheme

@Composable
fun Workflow(
    workflow: Workflow
) {

    WorkflowContainer {

        WorkflowCard {

            WorkflowHeader {

                Icons.Workflow()

                OElementMaximizedName(workflow.name)

                ControlPanel(false) {
                    IconButton(icon = Icons.PhaseAdd, onClick = {})
                    IconButton(icon = Icons.StepAdd, onClick = {})
                    IconButton(icon = Icons.Clone, onClick = {})
                    IconButton(icon = Icons.Copy, onClick = {})
                    IconButton(icon = Icons.Trash, onClick = {})
                }

            }

            InfoDescription(workflow.description)

        }
    }

}

@Composable
fun WorkflowContainer(
    content: @Composable () -> Unit
) {

    Div(attrs = {
        classes("workflow-container")
    }) {
        content()
    }

}

@Composable
fun WorkflowCard(
    content: @Composable () -> Unit
) {

    Div(attrs = {
        classes("workflow-card")
    }) {
        content()
    }

}

@Composable
fun WorkflowHeader(
    content: @Composable () -> Unit
) {

    Div(attrs = {
        classes("owle-row", "oelement-header")
    }) {
        content()
    }

}

@Composable
fun WorkflowHeader(name: String) {

    OElementPanel(
        text = {
            OwleText(
                name,
                OwleTheme.Text.Size.XL,
                OwleTheme.Color.Surface.TextOn,
                OwleTheme.Text.Style.BOLD
            ) {
                style {
                    minHeight(30.px)
                }
            }
        },
        icon = Icons.Workflow,
        panelContent = {
            IconButton(icon = Icons.Clone, onClick = {})
            IconButton(icon = Icons.ExpandItem, onClick = {})
            IconButton(icon = Icons.RemoveItem, onClick = {})
        }
    )
}
