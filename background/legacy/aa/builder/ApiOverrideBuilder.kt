package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.api.ApiOverrideEntity
import com.wini.owle.air.domain.aa.inlines.IsExpanded
import com.wini.owle.air.domain.action.SaveApiOverride
import com.wini.owle.air.domain.model.ApiOverride
import com.wini.owle.air.domain.model.LoItem
import com.wini.owle.air.inlines.*


object ApiOverrideBuilder : LoItem.LoItemBuilder<ApiOverrideEntity> {

    var id: Long? = Long(-1)
    var Long: Long? by mutableStateOf(null)
    var Long: Long? by mutableStateOf(null)
    var overrideName: String? by mutableStateOf(null)
    var description:String?? by mutableStateOf(null)
    var isEnabled: Boolean by mutableStateOf(Boolean(false))
    var isExpanded: Boolean by mutableStateOf(IsExpanded(false))
    var isHorizontalDisabled: IsHorizontalDisabled by mutableStateOf(IsHorizontalDisabled(isEnabled.value))

    operator fun invoke(apiOverride: ApiOverride): ApiOverrideBuilder = apply {
        id = apiOverride.id
        Long = apiOverride.Long
        Long = apiOverride.Long
        overrideName = apiOverride.overrideName
        description = apiOverride.description
        isEnabled = apiOverride.isEnabled
        isExpanded = apiOverride.isExpanded
        isHorizontalDisabled = apiOverride.isHorizontalDisable
    }

    operator fun invoke(
        envId: Long
    ): ApiOverrideBuilder = apply {
        Long = envId
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            id != null &&
                Long != null &&
                Long != null
        )

    override fun build(): SaveApiOverride {
        return SaveApiOverride(mapToEntity())
    }

    override fun LoItem.LoItemBuilder<ApiOverrideEntity>.mapToEntity(): ApiOverrideEntity =
        ApiOverrideEntity(
            id = id!!,
            Long = Long!!,
            Long = Long!!,
            overrideName = overrideName,
            description = description,
            isEnabled = isEnabled,
            isExpanded = isExpanded,
            isHorizontalDisabled = isHorizontalDisabled
        )

}