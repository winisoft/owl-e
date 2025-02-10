package ui.component.oelement.datapoint

import androidx.compose.runtime.Composable
import com.wini.owle.models.DataPointsSort
import ui.component.button.toggle.options.ToggleOptionGroup
import ui.component.img.Icons

@Composable
fun DataPointsSortSelect(
    selectedSort: DataPointsSort,
    isDisplayed: Boolean,
    onSortSelected: (DataPointsSort) -> Unit
) {
    ToggleOptionGroup(
        options = mapOf(
            DataPointsSort.NAME_ASC to Icons.NameSortAsc,
            DataPointsSort.NAME_DESC to Icons.NameSortDesc,
            DataPointsSort.CREATED_ASC to Icons.CreatedSortAsc,
            DataPointsSort.CREATED_DESC to Icons.CreatedSortDesc
        ),
        selectedOption = selectedSort,
        onOptionSelected = onSortSelected,
        isDisplayed = isDisplayed
    )
}
