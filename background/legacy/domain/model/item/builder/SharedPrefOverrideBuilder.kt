package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.invalid
import com.wini.owle.air.db.prefs.SharedPrefOverrideEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveSharedPrefOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.model.item.SharedPrefOverride
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.*

object SharedPrefOverrideBuilder : LoItem.LoItemBuilder<SharedPrefOverrideEntity> {

    var id: SharedPrefOverrideId by mutableStateOf(SharedPrefOverrideId(-1))
    var sharedPrefId: SharedPrefId? by mutableStateOf(null)
    var environmentId: EnvironmentId? by mutableStateOf(null)
    var friendlyId: String? by mutableStateOf(null)
    var dataType: DataType? by mutableStateOf(null)
    var overrideValue: OverrideValue? by mutableStateOf(null)
    var overrideName: OverrideName? by mutableStateOf(null)
    var description: Description? by mutableStateOf(null)
    var isEnabled: IsEnabled by mutableStateOf(IsEnabled(false))
    var isHorizontalDisable: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(sharedPrefOverride: SharedPrefOverride): SharedPrefOverrideBuilder = apply {
        id = sharedPrefOverride.id
        sharedPrefId = sharedPrefOverride.sharedPrefId
        environmentId = sharedPrefOverride.environmentId
        friendlyId = sharedPrefOverride.friendlyId
        dataType = sharedPrefOverride.dataType
        overrideName = sharedPrefOverride.overrideName
        overrideValue = sharedPrefOverride.overrideValue
        description = sharedPrefOverride.description
        isEnabled = sharedPrefOverride.isEnabled
        isHorizontalDisable = sharedPrefOverride.isHorizontalDisable
    }

    operator fun invoke(envId: EnvironmentId): SharedPrefOverrideBuilder = apply {
        environmentId = envId
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            sharedPrefId != null
                && environmentId != null
                && dataType != null
                && isHorizontalDisable != null
        )

    override fun build(): SaveActions<SharedPrefOverrideEntity> {
        return if (isValid.value) SaveSharedPrefOverride(mapToEntity()) else error("")
    }

    override fun LoItem.LoItemBuilder<SharedPrefOverrideEntity>.mapToEntity(): SharedPrefOverrideEntity {
        return SharedPrefOverrideEntity(
            id,
            sharedPrefId!!,
            environmentId!!,
            overrideValue,
            description,
            isEnabled,
            isHorizontalDisable!!
        )
    }

}