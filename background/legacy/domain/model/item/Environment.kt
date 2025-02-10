package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropFree
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.ParentItem
import com.wini.owle.air.inlines.*


data class Environment(
    val id: EnvironmentId,
    override val name: Name,
    val description: Description?,
    override val isEnabled: IsEnabled,
    override val isExpanded: IsExpanded,
    val isAddChildBtnVisible: Boolean,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
): LoItem(), ParentItem  {
    override val loid: Long
        get() = id.value
    override val typeName = "Environment"
    override val overrideName: OverrideName?
        get() = null
    override val displayTextMain = name?.value ?: "$typeName $loid"

    override val displayTextSub: String?
        get() = null

    override val depth = 1
    override val enabledIcon: ImageVector
        get() = Icons.Filled.ViewInAr
    override val disabledImplicitIcon: ImageVector
        get() = Icons.Default.CropFree
    override val breadcrumbText: String
        get() = name?.value ?: "$typeName $loid"

    fun isEnabledContextualIcon(
        rootItemEnabled: Boolean
    ): ImageVector = when {
        rootItemEnabled && isEnabled.value -> enabledIcon
        isEnabled.value.not() -> disabledExplicitIcon
        else -> disabledImplicitIcon
    }

    interface ChildOf {
        val environmentId: EnvironmentId
        val category: ItemCategory
    }

    companion object {
        const val MAX_CATEGORIES_COUNT = 4
    }
}