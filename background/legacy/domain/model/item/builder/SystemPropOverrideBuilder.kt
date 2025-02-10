package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.sysprop.SystemPropOverrideEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveSystemPropOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.SystemPropOverride
import com.wini.owle.air.domain.model.item.builder.SharedPrefOverrideBuilder.isHorizontalDisable
import com.wini.owle.air.domain.sysprop.SystemProp
import com.wini.owle.air.domain.sysprop.SystemPropOverrideAccessor
import com.wini.owle.air.inlines.*


object SystemPropOverrideBuilder : LoItem.LoItemBuilder<SystemPropOverrideEntity> {

    var id: SystemPropOverrideId by mutableStateOf(SystemPropOverrideId(-1))
    var systemPropId: SystemPropId? by mutableStateOf(null)
    var environmentId: EnvironmentId? by mutableStateOf(null)
    var overrideValue: OverrideValue? by mutableStateOf(null)
    var description: Description? by mutableStateOf(null)
    var isEnabled: IsEnabled by mutableStateOf(IsEnabled(true))
    var isHorizontalDisabled: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(envId: EnvironmentId): SystemPropOverrideBuilder = apply {
        environmentId = envId
    }

    operator fun invoke(item: SystemPropOverride): SystemPropOverrideBuilder = apply {
        id = item.id
        systemPropId = item.systemPropId
        environmentId = item.environmentId
        isHorizontalDisabled = item.isHorizontalDisable
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(systemPropId != null && environmentId != null && isHorizontalDisable != null)

    override fun build(): SaveActions<SystemPropOverrideEntity> {
        return if (isValid.value) SaveSystemPropOverride(mapToEntity()) else error("")
    }

    override fun LoItem.LoItemBuilder<SystemPropOverrideEntity>.mapToEntity(): SystemPropOverrideEntity {
        return SystemPropOverrideEntity(
           id,
           systemPropId!!,
           environmentId!!,
           overrideValue,
           description,
           isEnabled,
           isHorizontalDisabled!!,
        )
    }

}