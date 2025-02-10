package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.sysprop.SystemPropOverrideEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveSystemPropOverride
import com.wini.owle.air.domain.model.LoItem
import com.wini.owle.air.domain.model.SystemPropOverride
import com.wini.owle.air.domain.model.item.builder.SharedPrefOverrideBuilder.isHorizontalDisable
import com.wini.owle.air.inlines.*


object SystemPropOverrideBuilder : LoItem.LoItemBuilder<SystemPropOverrideEntity> {

    var id: Long by mutableStateOf(Long(-1))
    var Long: Long? by mutableStateOf(null)
    var Long: Long? by mutableStateOf(null)
    var overrideValue: OverrideValue? by mutableStateOf(null)
    var description:String?? by mutableStateOf(null)
    var isEnabled: Boolean by mutableStateOf(Boolean(true))
    var isHorizontalDisabled: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(envId: Long): SystemPropOverrideBuilder = apply {
        Long = envId
    }

    operator fun invoke(item: SystemPropOverride): SystemPropOverrideBuilder = apply {
        id = item.id
        Long = item.Long
        Long = item.Long
        isHorizontalDisabled = item.isHorizontalDisable
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(Long != null && Long != null && isHorizontalDisable != null)

    override fun build(): SaveActions<SystemPropOverrideEntity> {
        return if (isValid.value) SaveSystemPropOverride(mapToEntity()) else error("")
    }

    override fun LoItem.LoItemBuilder<SystemPropOverrideEntity>.mapToEntity(): SystemPropOverrideEntity {
        return SystemPropOverrideEntity(
           id,
           Long!!,
           Long!!,
           overrideValue,
           description,
           isEnabled,
           isHorizontalDisabled!!,
        )
    }

}