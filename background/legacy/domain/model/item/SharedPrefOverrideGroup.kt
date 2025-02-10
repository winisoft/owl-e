package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.outlined.RuleFolder
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.inlines.*


data class SharedPrefOverrideGroup(
    override val environmentId: EnvironmentId,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean
): ItemGrouping() {

    override val loid: Long
        get() = -1

    override val depth: Int
        get() = 2
    override val displayTextMain: String
        get() = typeName

    override val displayTextSub: String?
        get() = null

    override val enabledIcon: ImageVector
        get() = Icons.Default.RuleFolder

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.RuleFolder

    override val breadcrumbText: String
        get() = typeName

    override val name: Name
        get() = Name("Shared Preferences")

    override val overrideName: OverrideName
        get() = OverrideName("Shared Preferences")

    override val category: ItemCategory
        get() = ItemCategory.SharedPrefs

    override val typeName: String
        get() = "Shared Preferences"

    override val isHorizontalDisable: IsHorizontalDisabled
        get() = IsHorizontalDisabled(false)

    companion object {
        val groupId: GroupId = GroupId(4)
        const val HEADER_TYPE_KEY = 4
    }

}