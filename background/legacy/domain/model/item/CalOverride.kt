package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.Build
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.*


data class CalOverride(
    val id: CalOverrideId,
    val calId: CalibrationId,
    override val environmentId: EnvironmentId,
    val defaultName: CalName,
    private val calDataType: CalType,
    val defaultValue: CalValue?,
    override val overrideName: OverrideName?,
    val overrideValue: OverrideValue?,
    override val isEnabled: IsEnabled,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
) : LoItem(),
    Environment.ChildOf {

    override val loid: Long
        get() = id.value

    /**
     * In short: the property's name.
     *
     * In key-value pairs such as [SharedPrefOverride] && [SystemPropOverride], this is the defined identifier 'key',
     * e.g. 'welcome_was_shown' or 'persist.auth.vin'. For [CarPropOverride] or [CalOverride] properties, the convention
     * in place is an 'id' that is not meaningful at a glance. Instead, conversationally (and here, for display purposes),
     * we use their 'name' property -- GIS501_HOMESCREENUI or TIRE_PRESSURE_DISPLAY_UNITS, for instance.
     */
    val friendlyId: String
        get() = defaultName.value

    override val typeName: String
        get() = "Calibration Property Override"


    override val name: Name
        get() = Name(defaultName.value)

    override val displayTextMain
        get() = overrideName?.value ?: defaultName.value

    override val displayTextSub: String
        get() = "${dataType.name}: ${overrideValue?.value}"

    val description: Description?
        get() = null

    override val depth
        get() = 3

    override val enabledIcon: ImageVector
        get() = Icons.Filled.Build

    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.Build

    override val category : ItemCategory
        get() = ItemCategory.Cals

    val dataType: DataType
        get() = DataType.fromCalType(calDataType)

    override val breadcrumbText: String
        get() = overrideName?.value ?: friendlyId

}