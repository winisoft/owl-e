package com.wini.owle.air.domain.model.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.db.car.CarOverrideEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveCarOverride
import com.wini.owle.air.domain.car.CarProp
import com.wini.owle.air.domain.car.CarPropOverrideAccessor
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.domain.car.VehicleArea
import com.wini.owle.air.domain.car.VehiclePropertyGroup
import com.wini.owle.air.inlines.*


data class CarPropOverride(
    val id: CarOverrideId,
    val carPropId: CarPropId,
    override val environmentId: EnvironmentId,
    override val name: Name,
    override val overrideName: OverrideName?,
    val description: Description?,
    val overrideValue: OverrideValue?,
    override val isEnabled: IsEnabled,
    override val isVisible: Boolean,
    override val isImplicitDisable: Boolean,
    override val isHorizontalDisable: IsHorizontalDisabled
) : LoPropertyItem() {

    override val displayTextMain = overrideName?.value ?: name.value

    override val displayTextSub: String
        get() = "${dataType.name}: ${overrideValue?.value ?: "[NULL]"}"
    override val depth: Int
        get() = 3
    override val enabledIcon: ImageVector
        get() = Icons.Default.Build
    override val disabledImplicitIcon: ImageVector
        get() = Icons.Outlined.Build

    override val loid: Long
        get() = id.value

    val friendlyId: String
        get() = name.value ?: error("A property item will always have a valid name, whether the key of its property, or name for cals/car")

    val dataType: DataType
        get() = hexString?.run { DataType.fromHexString(this) } ?: DataType.Invalid

    override val category: ItemCategory
        get() = ItemCategory.CarProps

    override val typeName: String
        get() = "Car Service Property Override"

    val hexString: String?
        get() = carPropId.value.toInt()
            .takeIf { it != 0 }
            ?.run { "0x${Integer.toHexString(this)}" }

    val vehiclePropertyGroup: VehiclePropertyGroup?
        get() = hexString?.run { VehiclePropertyGroup.fromHex(this) }

    val vehicleArea: VehicleArea?
        get() = hexString?.run { VehicleArea.fromHex(this) }

    override val breadcrumbText: String
        get() = overrideName?.value ?: friendlyId

}