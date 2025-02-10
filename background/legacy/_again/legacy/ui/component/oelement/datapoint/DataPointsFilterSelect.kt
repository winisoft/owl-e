package ui.component.oelement.datapoint

import androidx.compose.runtime.Composable
import com.wini.owle.models.DataPointsFilter
import ui.component.button.toggle.options.ToggleOptionGroup
import ui.component.img.Icons

@Composable
fun DataPointsFilterSelect(
    selectedFilter: DataPointsFilter,
    isDisplayed: Boolean,
    onFilterSelected: (DataPointsFilter) -> Unit
) {
    ToggleOptionGroup(
        options = mapOf(
            DataPointsFilter.NAME to Icons.NameFilter,
            DataPointsFilter.DATA_TYPE to Icons.DataType,
            DataPointsFilter.PACKAGE to Icons.PackageFilter
        ),
        selectedOption = selectedFilter,
        onOptionSelected = onFilterSelected,
        isDisplayed = isDisplayed
    )
}
