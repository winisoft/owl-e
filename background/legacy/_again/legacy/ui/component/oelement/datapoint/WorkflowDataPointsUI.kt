package ui.component.oelement.datapoint

import androidx.compose.runtime.*
import com.wini.owle.models.DataPoint
import com.wini.owle.models.DataPointsFilter
import com.wini.owle.models.DataPointsSort
import ui.component.items.DataPointCardList

@Composable
fun WorkflowDataPointsUI(
    dataPoints: List<DataPoint>
) {
    var displayedItems by remember { mutableStateOf(dataPoints) }
    var isExpanded by remember { mutableStateOf(false) }
    var isSearchInputDisplayed by remember { mutableStateOf(false) }
    var isShowingSortOptions by remember { mutableStateOf(false) }
    var isShowingFilterOptions by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var sortOrder by remember { mutableStateOf(DataPointsSort.NAME_ASC) }
    var filter by remember { mutableStateOf(DataPointsFilter.NAME) }


    if (!isExpanded && !isSearchInputDisplayed)
        WorkflowDataHeaderUI(
            dataPointsCountTotal = dataPoints.size,
            onExpandList = { isExpanded = true },
            onSearchClick = { isSearchInputDisplayed = true }
        )
    if (isSearchInputDisplayed) {
        WorkflowDataQueryUI(
            dataPoints = dataPoints,
            queryText = searchQuery,
            filter = filter,
            sortOrder = sortOrder,
            isShowingSortOptions = isShowingSortOptions,
            isShowingFilterOptions = isShowingFilterOptions,
            onShowSortOptions = { isShowing ->
                isShowingSortOptions = isShowing
            },
            onShowFilterOptions = { isShowing ->
                isShowingFilterOptions = isShowing
            },
            onFilterChange = { filterUpdate ->
                filter = filterUpdate
            },
            onSortOrderChange = { orderUpdate ->
                sortOrder = orderUpdate
            },
            onQueryTextChange = { queryTextUpdate ->
                searchQuery = queryTextUpdate
                displayedItems = dataPoints.filter {
                    when (filter) {
                        DataPointsFilter.NAME -> it.name.contains(searchQuery, ignoreCase = true)
                        DataPointsFilter.DATA_TYPE -> it.typeDeclaration.typeDefinition.name.contains(searchQuery, ignoreCase = true)
                        DataPointsFilter.PACKAGE -> it.typeDeclaration.pkg.name.contains(searchQuery, ignoreCase = true)
                    }
                }.run {
//                    when (sortOrder) {
//                        DataPointsSort.NAME_ASC -> sortedBy { it.name }
//                        DataPointsSort.NAME_DESC -> sortedBy { it.name }.reversed()
//                        DataPointsSort.CREATED_ASC -> sortedBy { it.created }
//                        DataPointsSort.CREATED_DESC -> sortedBy { it.created }.reversed()
//                    }
                    displayedItems
                }
            },
        )
    }
    if (isExpanded) {
        DataPointCardList(displayedItems)
    }
}