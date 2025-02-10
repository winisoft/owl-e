package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.Build
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.*


data class SharedPrefOverride(
    val id: SharedPrefOverrideId,
    val sharedPrefId: SharedPrefId,
    override val environmentId: EnvironmentId,
    val friendlyId: String,
    val dataType: DataType,
    val overrideValue: OverrideValue?,
    override val overrideName: OverrideName?,
    val description: Description?,
    override val isEnabled: IsEnabled,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
): LoItem(), Environment.ChildOf {

    override val displayTextMain = overrideName?.value ?: friendlyId

    override val displayTextSub = "${dataType.name}: $overrideValue"

    override val loid: Long
        get() = id.value

    override val depth: Int
        get() = 3

    override val name: Name
        get() = Name(friendlyId)

    override val enabledIcon: ImageVector
        get() = Icons.Default.Build

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.Build

    override val typeName
        get() = "Shared Preference Override"

    override val breadcrumbText: String
        get() = overrideName?.value ?: friendlyId

    override val category: ItemCategory
        get() = ItemCategory.SharedPrefs

}