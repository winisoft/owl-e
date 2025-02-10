package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.outlined.RuleFolder
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.inlines.*


data class ApisGroup(
    override val environmentId: EnvironmentId,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean
): ItemGrouping() {

    override val loid: Long
        get() = -1

    override val name: Name
        get() = Name("APIs")

    override val overrideName: OverrideName
        get() = OverrideName("APIs")

    override val displayTextMain: String
        get() = typeName

    override val displayTextSub: String?
        get() = null

    override val depth: Int
        get() = 2

    override val enabledIcon: ImageVector
        get() = Icons.Default.RuleFolder

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.RuleFolder

    override val breadcrumbText: String
        get() = typeName

    override val category: ItemCategory
        get() = ItemCategory.FakeApis

    override val typeName: String
        get() = "APIs"

    override val isHorizontalDisable: IsHorizontalDisabled
        get() = IsHorizontalDisabled(false)

    companion object {
        val groupId: GroupId = GroupId(0)
    }

}