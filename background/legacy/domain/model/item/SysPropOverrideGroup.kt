package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.outlined.RuleFolder
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.inlines.*


data class SysPropOverrideGroup(
    override val environmentId: EnvironmentId,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean
): ItemGrouping() {

    override val loid: Long
        get() = -1

    override val name: Name
        get() = Name("System Properties")

    override val overrideName: OverrideName
        get() = OverrideName("System Properties")

    override val typeName: String
        get() = "System Properties"

    override val category: ItemCategory
        get() = ItemCategory.SystemProps

    override val isHorizontalDisable: IsHorizontalDisabled
        get() = IsHorizontalDisabled(false)

    companion object {
        val groupId: GroupId = GroupId(3)
        const val GROUP_KEY = 3
    }

}