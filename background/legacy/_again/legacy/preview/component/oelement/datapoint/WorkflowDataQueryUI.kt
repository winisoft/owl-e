package preview.component.oelement.datapoint

import androidx.compose.runtime.Composable
import com.wini.owle.models.DataPointsFilter
import com.wini.owle.models.DataPointsSort
import ui.component.oelement.datapoint.WorkflowDataQueryUI

@Composable
fun PreviewWorkflowDataQueryUI() {

    WorkflowDataQueryUI(
        dataPoints = listOf(),
        queryText = "",
        isShowingSortOptions = false,
        onQueryTextChange = { _ -> },
        onShowSortOptions = { _ -> },
        onSortOrderChange = { _ -> },
        filter = DataPointsFilter.NAME,
        sortOrder = DataPointsSort.NAME_ASC,
        isShowingFilterOptions = false,
        onFilterChange = { _ -> },
        onShowFilterOptions = { _ -> }
    )

}