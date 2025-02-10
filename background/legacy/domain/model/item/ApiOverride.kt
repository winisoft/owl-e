package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.material.icons.outlined.ScatterPlot
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.inlines.*


data class ApiOverride(
    val id: ApiOverrideId,
    val apiId: ApiId,
    override val environmentId: EnvironmentId,
    val loRoute: LoRoute,
    override val name: Name,
    val openApi: String?,
    override val overrideName: OverrideName?,
    val description: Description?,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
): LoItem(),
    Environment.ChildOf,
    ParentItem {

    override val loid: Long
        get() = id.value

    override val displayTextMain: String
        get() = overrideName?.value
            ?.run { ifEmpty { null } }
            ?: name.value
            ?: "$typeName $loid"

    override val displayTextSub: String
        get() = if (overrideName?.value?.isEmpty() == false) name.value else ""

    override val typeName
        get() = "API Override"

    override val depth
        get() = 3

    override val enabledIcon: ImageVector
        get() = Icons.Default.GroupWork

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.ScatterPlot

    override val breadcrumbText: String
        get() = overrideName?.value?.run { ifEmpty { name.value } } ?: "$typeName $loid"

    override val category: ItemCategory
        get() = ItemCategory.FakeApis

    fun isEnabledContextualIcon(
        rootItemEnabled: Boolean,
        environmentEnabled: Boolean,
        apiThingsHeaderEnabled: Boolean
    ): ImageVector = when {
        rootItemEnabled
        && environmentEnabled
        && apiThingsHeaderEnabled
        && isEnabled.value -> enabledIcon
        isEnabled.value.not() -> disabledExplicitIcon
        else -> disabledImplicitIcon
    }

    interface ChildOf {
        val apiOverrideId: ApiOverrideId
    }
}