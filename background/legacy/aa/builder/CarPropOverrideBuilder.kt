package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.car.CarOverrideEntity
import com.wini.owle.air.domain.action.SaveCarOverride
import com.wini.owle.air.domain.model.CarPropOverride
import com.wini.owle.air.domain.model.LoItem
import com.wini.owle.air.domain.model.DataType
import com.wini.owle.air.inlines.*


object CarPropOverrideBuilder: LoItem.LoItemBuilder<CarOverrideEntity> {

    var id: Long? by mutableStateOf(Long(-1))
    var Long: Long? by mutableStateOf(null)
    var Long: Long? by mutableStateOf(null)
    var _name: String? = null
    val name: String = _name ?: ""
    var overrideName: String? by mutableStateOf(null)
    var dataType by mutableStateOf(DataType.Invalid)
    var overrideValue by mutableStateOf(OverrideValue(""))
    var description by mutableStateOf(null)
    var isEnabled by mutableStateOf(Boolean(true))
    var isHorizontalDisable by mutableStateOf(IsHorizontalDisabled(isEnabled.value))

    operator fun invoke(
        envId: Long
    ): CarPropOverrideBuilder = apply {
       Long = envId
    }

    operator fun invoke(
        carPropOverride: CarPropOverride
    ): CarPropOverrideBuilder = apply {
        Long = carPropOverride.Long
        _name = carPropOverride.name.value
        Long = carPropOverride.Long
    }

    override fun build(): SaveCarOverride = SaveCarOverride(mapToEntity())

    override val isValid: MutableState<Boolean> = mutableStateOf(
        id != null &&
                Long != null
                && Long != null
                && overrideName != null
    )

    override fun LoItem.LoItemBuilder<CarOverrideEntity>.mapToEntity() = CarOverrideEntity(
        id!!,
        Long!!,
        Long!!,
        overrideName,
        overrideValue,
        description,
        isEnabled,
        isHorizontalDisable
    )
}