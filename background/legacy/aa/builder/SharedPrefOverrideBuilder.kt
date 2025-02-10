package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.prefs.SharedPrefOverrideEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveSharedPrefOverride
import com.wini.owle.air.domain.model.LoItem
import com.wini.owle.air.domain.model.SharedPrefOverride
import com.wini.owle.air.domain.model.DataType
import com.wini.owle.air.inlines.*

object SharedPrefOverrideBuilder : LoItem.LoItemBuilder<SharedPrefOverrideEntity> {

    var id: Long by mutableStateOf(Long(-1))
    var sharedPrefId: Long? by mutableStateOf(null)
    var Long: Long? by mutableStateOf(null)
    var friendlyId: String? by mutableStateOf(null)
    var dataType: DataType? by mutableStateOf(null)
    var overrideValue: OverrideValue? by mutableStateOf(null)
    var overrideName: String? by mutableStateOf(null)
    var description:String?? by mutableStateOf(null)
    var isEnabled: Boolean by mutableStateOf(Boolean(false))
    var isHorizontalDisable: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(sharedPrefOverride: SharedPrefOverride): SharedPrefOverrideBuilder = apply {
        id = sharedPrefOverride.id
        sharedPrefId = sharedPrefOverride.sharedPrefId
        Long = sharedPrefOverride.Long
        friendlyId = sharedPrefOverride.friendlyId
        dataType = sharedPrefOverride.dataType
        overrideName = sharedPrefOverride.overrideName
        overrideValue = sharedPrefOverride.overrideValue
        description = sharedPrefOverride.description
        isEnabled = sharedPrefOverride.isEnabled
        isHorizontalDisable = sharedPrefOverride.isHorizontalDisable
    }

    operator fun invoke(envId: Long): SharedPrefOverrideBuilder = apply {
        Long = envId
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            sharedPrefId != null
                && Long != null
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
            Long!!,
            overrideValue,
            description,
            isEnabled,
            isHorizontalDisable!!
        )
    }

}