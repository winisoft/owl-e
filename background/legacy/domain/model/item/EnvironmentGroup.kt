package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.outlined.RuleFolder
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.inlines.*

sealed class EnvironmentGroup(
    override val environmentId: EnvironmentId,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean
): ItemGrouping(), Environment.ChildOf, ParentItem {

    override val loid: Long
        get() = -1

    abstract val groupId: GroupId

    abstract val count: Int

    override val enabledIcon: ImageVector
        get() = Icons.Outlined.RuleFolder

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.RuleFolder

    override val depth: Int
        get() = 2

    override val displayTextMain: String
        get() = typeName

    override val displayTextSub: String?
        get() = null

    override val breadcrumbText: String
        get() = ""

    companion object {


    }
}