package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.outlined.RuleFolder
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.inlines.*

sealed class ItemGrouping: LoItem(), ParentItem, Environment.ChildOf {

    override val displayTextMain: String
        get() = typeName

    override val displayTextSub: String?
        get() = null

    override val breadcrumbText: String
        get() = typeName

    override val depth: Int
        get() = 2

    override val enabledIcon: ImageVector
        get() = Icons.Default.RuleFolder

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.RuleFolder

    companion object {
        fun build(
            envId: EnvironmentId,
            groupId: GroupId,
            isEnabled: IsEnabled,
            isExpanded: IsExpanded,
            isVisible: Boolean,
            isImplicitDisable: Boolean
        ): ItemGrouping = when (groupId) {
            ApisGroup.groupId ->
                ApisGroup(envId, isEnabled, isExpanded, isVisible, isImplicitDisable)
            CalOverrideGroup.groupId ->
                CalOverrideGroup(envId, isEnabled, isExpanded, isVisible, isImplicitDisable)
            CarOverrideGroup.groupId ->
                CarOverrideGroup(envId, isEnabled, isExpanded, isVisible, isImplicitDisable)
            SysPropOverrideGroup.groupId ->
                SysPropOverrideGroup(envId, isEnabled, isExpanded, isVisible, isImplicitDisable)
            SharedPrefOverrideGroup.groupId ->
                SharedPrefOverrideGroup(envId, isEnabled, isExpanded, isVisible, isImplicitDisable)
            else ->
                error("Unanticipated item group id")
        }
    }
}


sealed class ItemCategory(val id: GroupId) {

    object FakeApis: ItemCategory(GroupId(0))

    object Cals: ItemCategory(GroupId(1))

    object CarProps: ItemCategory(GroupId(2))

    object SystemProps: ItemCategory(GroupId(3))

    object SharedPrefs: ItemCategory(GroupId(4))

}