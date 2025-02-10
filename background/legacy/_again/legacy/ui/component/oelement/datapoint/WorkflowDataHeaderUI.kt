package ui.component.oelement.datapoint

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ui.component.button.IconButton
import ui.component.img.Icons
import ui.component.row.OElementPanel
import ui.component.row.PanelContainer
import ui.theme.OwleTheme
import ui.component.text.OwleText

/**
 * The readonly region displayed by default to represent the fully 'collapsed' UI region of the workflow enabling
 * interacting with the workflow's data points, simply displaying the 'count of' summary and displaying the typical
 * hover-over icon buttons
 */
@Composable
fun WorkflowDataHeaderUI(
    dataPointsCountTotal: Int,
    onExpandList: () -> Unit,
    onSearchClick: () -> Unit
) {

    Div({
        onClick {

        }
        classes("shadow-lg", "rounded-lg", "p-4")
        style {
            backgroundColor(OwleTheme.Color.Surface.value)
            border(width=2.px, color=OwleTheme.Color.Surface.CardBorder.value)
            minWidth(380.px)
        }
    }) {
        OElementPanel(
            text = {
                OwleText(
                    text = "$dataPointsCountTotal ${if (dataPointsCountTotal == 1) "Data Point" else "Data Points"}",
                    textSize = OwleTheme.Text.Size.XL,
                    color = OwleTheme.Color.Surface.TextOn,
                    style = OwleTheme.Text.Style.BOLD
                ) {
                    style {
                        minHeight(30.px)
                    }
                }
            },
            icon = Icons.Data,
            panelContent = {
                PanelContainer {
                    IconButton(icon = Icons.DataSearch, onClick = onSearchClick)
                    IconButton(icon = Icons.ExpandItem, onClick = onExpandList)
                }
            }
        )
    }

}
