package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RuleFolder
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.inlines.*


data class CalOverrideGroup(
    override val environmentId: EnvironmentId,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean
): ItemGrouping() {

    override val loid: Long
        get() = -1

    override val displayTextMain: String
        get() = typeName

    override val displayTextSub: String?
        get() = null

    override val depth: Int
        get() = 2

    override val breadcrumbText: String
        get() = typeName

    override val category: ItemCategory
        get() = ItemCategory.Cals

    override val name: Name
        get() = Name("Calibration Overrides")

    override val overrideName: OverrideName
        get() = OverrideName("Calibration Overrides")

    override val isHorizontalDisable: IsHorizontalDisabled
        get() = IsHorizontalDisabled(false)

    override val typeName: String
        get() = "Calibrations"

    companion object {
        val groupId: GroupId = GroupId(1)
    }

}