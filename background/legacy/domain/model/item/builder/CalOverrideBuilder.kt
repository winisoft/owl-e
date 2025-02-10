package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.cal.CalOverrideEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveCalOverride
import com.wini.owle.air.domain.api.Api
import com.wini.owle.air.domain.api.ApiOverrideAccessor
import com.wini.owle.air.domain.cal.CalOverrideAccessor
import com.wini.owle.air.domain.cal.Calibration
import com.wini.owle.air.domain.model.item.ApiOverride
import com.wini.owle.air.domain.model.item.CalOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.inlines.*

object CalOverrideBuilder : LoItem.LoItemBuilder<CalOverrideEntity> {

    var id: CalOverrideId = CalOverrideId(-1)
    var calId: Pair<() -> Boolean, CalibrationId?> by mutableStateOf({ calId.second != null } to null)
    var environmentId: Pair<() -> Boolean, EnvironmentId?> by mutableStateOf({ environmentId.second != null } to null)
    var overrideName: OverrideName? by mutableStateOf(null)
    var overrideValue: OverrideValue? by mutableStateOf(null)
    var description: Description? by mutableStateOf(null)
    var isEnabled: IsEnabled by mutableStateOf(IsEnabled(false))
    var isHorizontalDisabled: Pair<() -> Boolean, IsHorizontalDisabled?> by mutableStateOf({ isHorizontalDisabled.second != null } to null)

    operator fun invoke(calOverride: CalOverride): CalOverrideBuilder = apply {
        id = calOverride.id
        calId = calId.first to calOverride.calId
        environmentId = environmentId.first to calOverride.environmentId
        overrideName = overrideName
        overrideValue = overrideValue
        description = calOverride.description
        isEnabled = calOverride.isEnabled
        isHorizontalDisabled = isHorizontalDisabled.first to calOverride.isHorizontalDisable
    }

    operator fun invoke(envId: EnvironmentId): CalOverrideBuilder = apply {
        environmentId = environmentId.first to envId
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            isHorizontalDisabled.first() && calId.first() && environmentId.first()
        )

    override fun build(): SaveCalOverride = SaveCalOverride(
        if (isValid.value) mapToEntity() else error("The isValid field should inform you when it is safe to call this")
    )

    override fun LoItem.LoItemBuilder<CalOverrideEntity>.mapToEntity(): CalOverrideEntity {
        return CalOverrideEntity(
            id = id,
            calId = calId.second!!,
            environmentId = environmentId.second!!,
            overrideName = overrideName,
            overrideValue = overrideValue,
            description = description,
            isEnabled = isEnabled,
            isHorizontalDisabled = isHorizontalDisabled.second!!
        )
    }

}