package ui.component.oelement.datapoint

import androidx.compose.runtime.Composable
import com.wini.owle.models.DataPoint
import com.wini.owle.models.DataPointsFilter
import com.wini.owle.models.DataPointsSort
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import ui.component.button.IconButton
import ui.component.img.Icon
import ui.component.img.Icons
import ui.component.row.OElementPanel
import ui.component.row.PanelContainer


//@Composable
//fun WorkflowDataQueryUI(
//    dataPoints: List<DataPoint>,
//    queryText: String,
//    filter: DataPointsFilter,
//    sortOrder: DataPointsSort,
//    isShowingSortOptions: Boolean,
//    isShowingFilterOptions: Boolean,
//    onSortOrderChange: (DataPointsSort) -> Unit,
//    onFilterChange: (DataPointsFilter) -> Unit,
//    onQueryTextChange: (String) -> Unit,
//    onShowSortOptions: (Boolean) -> Unit,
//    onShowFilterOptions: (Boolean) -> Unit

//
//        OElementPanel(
//            text = {
//                Div({
//                    classes("input-container", "flex-1", "mx-2")
//                    style {
//                        border(width = 1.px, color = colorOwleSurfaceBorder)
//                        borderRadius(8.px)
//                        padding(4.px)
//                        display(DisplayStyle.Flex)
//                        alignItems(AlignItems.Center)
//                    }
//                }) {
//                    TextArea(
//                        value = queryText,
//                        attrs = {
//                            classes("text-input", "w-full", "bg-transparent", "outline-none")
//                            onInput { onQueryTextChange(it.value) }
//                            style {
//
//                            }
//                        }
//
//                    )
//                }
//            },
//            iconLeft = Icons.DataSearch,
//            controlPanelButtons = listOfNotNull(
//                if (queryText.isNotEmpty()) {
//                    {
//                        IconButton(
//                            icon = Icons.RemoveItem,
//                            onClick = { onQueryTextChange("") }
//                        )
//                    }
//                } else null,
//                {
//                    IconButton(
//                        icon = Icons.Sort,
//                        onClick = { }
//                    )
//                },
//                {
//                    IconButton(
//                        icon = Icons.Filter,
//                        onClick = { }
//                    )
//                }
//            )
//        )
//
//        // Left Search Icon
//        Div({
//            classes("icon-container", "p-2")
//        }) {
//            Icon()
//        }
//
//        // Search Input
//
//
//        // Right Control Buttons
//        Div({
//            classes("control-buttons", "flex", "items-center")
//        }) {
//
//        }
//    }
//}

@Composable
fun WorkflowDataQueryUI(
    dataPoints: List<DataPoint>,
    queryText: String,
    filter: DataPointsFilter,
    sortOrder: DataPointsSort,
    isShowingSortOptions: Boolean,
    isShowingFilterOptions: Boolean,
    onSortOrderChange: (DataPointsSort) -> Unit,
    onFilterChange: (DataPointsFilter) -> Unit,
    onQueryTextChange: (String) -> Unit,
    onShowSortOptions: (Boolean) -> Unit,
    onShowFilterOptions: (Boolean) -> Unit
) {

    OElementPanel(
        text = {
            TextArea(
                //) {
//    Div({
//        classes("shadow-lg", "rounded-lg", "p-4", "flex", "items-center")
//        style {
//            backgroundColor(colorOwleSurface)
//            border(width = 2.px, color = colorOwleSurfaceBorder)
//            minWidth(380.px)
//        }
//    }) {      value = queryText,
                attrs = {
                    classes("text-input", "w-full", "resize-none", "outline-none")
                    onInput { onQueryTextChange(it.value) }
                }
            )
        },
        icon = Icons.DataSearch,
        panelContent = {
            PanelContainer {
                IconButton(
                    icon = Icons.Sort,
                    onClick = { onShowSortOptions(!isShowingSortOptions) }
                )
                IconButton(
                    icon = Icons.Filter,
                    onClick = { onShowFilterOptions(!isShowingFilterOptions) }
                )
                IconButton(
                    icon = Icons.RemoveItem,
                    onClick = { onQueryTextChange("") }
                )
            }
        }
    )
}
