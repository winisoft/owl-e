package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.inlines.*


data class EndpointOverride(
    val id: EndpointOverrideId,
    val endpointId: EndpointId,
    override val environmentId: EnvironmentId,
    override val apiOverrideId: ApiOverrideId,
    override val overrideName: OverrideName?,
    val description: Description?,
    override val name: Name,
    val path: EndpointPath,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
): LoItem(),
    Environment.ChildOf,
    ApiOverride.ChildOf,
    ParentItem {

    override val loid: Long
        get() = id.value

    override val typeName: String = "Endpoint"

    override val depth: Int = 4

    override val enabledIcon: ImageVector
        get() = Icons.Default.Adjust

    override val disabledImplicitIcon: ImageVector
        get() =  Icons.Default.FilterTiltShift

    override val displayTextMain: String = overrideName?.value?.ifEmpty { null } ?: name.value

    override val displayTextSub: String
        get() = path.value

    override val category: ItemCategory
        get() = ItemCategory.FakeApis

    override val breadcrumbText: String
        get() = overrideName?.value?.run { ifEmpty { name.value } } ?: path.value


    interface ChildOf {
        val endpointOverrideId: EndpointOverrideId
    }

    companion object {
        const val CHILD_DISABLED_MSG = "The API Endpoint it models a call to is disabled."
    }
}