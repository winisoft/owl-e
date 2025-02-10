package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.Build
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.*


data class SystemPropOverride(
    val id: SystemPropOverrideId,
    val systemPropId: SystemPropId,
    val environmentId: EnvironmentId,
    val key: SysPropKey,
    val overrideValue: OverrideValue?,
    override val isEnabled: IsEnabled,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
): LoItem() {

    override val displayTextMain
        get() = overrideName.value

    override val displayTextSub: String
        get() = "${dataType.name}: ${overrideValue?.value}"

    override val loid: Long
        get() = id.value

    val description: Description?
        get() = null

    val friendlyId: String
        get() = key.value

    val dataType: DataType
        get() = DataType.String

    override val typeName
        get() = "User Property"

    override val name: Name
        get() = Name(key.value)

    override val overrideName: OverrideName
        get() = OverrideName("")

    override val breadcrumbText: String
        get() = friendlyId

    override val depth: Int
        get() = 3

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.Build

    override val enabledIcon: ImageVector
        get() = Icons.Default.Build

}