package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.car.CarOverrideEntity
import com.wini.owle.air.domain.action.SaveCarOverride
import com.wini.owle.air.domain.car.CarProp
import com.wini.owle.air.domain.car.CarPropOverrideAccessor
import com.wini.owle.air.domain.model.item.CarPropOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.*


object CarPropOverrideBuilder: LoItem.LoItemBuilder<CarOverrideEntity> {

    var id: CarOverrideId? by mutableStateOf(CarOverrideId(-1))
    var carPropId: CarPropId? by mutableStateOf(null)
    var environmentId: EnvironmentId? by mutableStateOf(null)
    var _name: String? = null
    val name: String = _name ?: ""
    var overrideName: OverrideName? by mutableStateOf(null)
    var dataType by mutableStateOf(DataType.Invalid)
    var overrideValue by mutableStateOf(OverrideValue(""))
    var description by mutableStateOf(null)
    var isEnabled by mutableStateOf(IsEnabled(true))
    var isHorizontalDisable by mutableStateOf(IsHorizontalDisabled(isEnabled.value))

    operator fun invoke(
        envId: EnvironmentId
    ): CarPropOverrideBuilder = apply {
       environmentId = envId
    }

    operator fun invoke(
        carPropOverride: CarPropOverride
    ): CarPropOverrideBuilder = apply {
        carPropId = carPropOverride.carPropId
        _name = carPropOverride.name.value
        environmentId = carPropOverride.environmentId
    }

    override fun build(): SaveCarOverride = SaveCarOverride(mapToEntity())

    override val isValid: MutableState<Boolean> = mutableStateOf(
        id != null &&
                carPropId != null
                && environmentId != null
                && overrideName != null
    )

    override fun LoItem.LoItemBuilder<CarOverrideEntity>.mapToEntity() = CarOverrideEntity(
        id!!,
        carPropId!!,
        environmentId!!,
        overrideName,
        overrideValue,
        description,
        isEnabled,
        isHorizontalDisable
    )
}