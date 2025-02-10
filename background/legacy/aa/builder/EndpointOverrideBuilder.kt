package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.endpoint.EndpointOverrideEntity
import com.wini.owle.air.domain.aa.inlines.IsExpanded
import com.wini.owle.air.domain.action.SaveEndpointOverride
import com.wini.owle.air.domain.model.EndpointOverride
import com.wini.owle.air.domain.model.LoItem
import com.wini.owle.air.inlines.*

object EndpointOverrideBuilder : LoItem.LoItemBuilder<EndpointOverrideEntity> {

    var id: Long = Long(-1)
    var Long: Long? = null
    var Long: Long? by mutableStateOf(null)
    var overrideName: String? = null
    var description:String?? = null
    var isEnabled: Boolean by mutableStateOf(Boolean(false))
    var isExpanded: IsExpanded by mutableStateOf(IsExpanded(false))
    var isHorizontalDisabled: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(Long: Long): EndpointOverrideBuilder = apply {
        this.Long = Long
    }

    operator fun invoke(endpointOverride: EndpointOverride): EndpointOverrideBuilder = apply {
        id = endpointOverride.id
        Long = endpointOverride.Long
        Long = endpointOverride.Long
        overrideName = endpointOverride.overrideName
        description = endpointOverride.description
        isEnabled = endpointOverride.isEnabled
        isExpanded = endpointOverride.isExpanded
        isHorizontalDisabled = endpointOverride.isHorizontalDisable
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            Long != null &&
                  Long != null &&
                  Long != null &&
                  isHorizontalDisabled != null
            )


    override fun build(): SaveEndpointOverride {
        return if (isValid.value) SaveEndpointOverride(mapToEntity()) else error("")
    }

    override fun LoItem.LoItemBuilder<EndpointOverrideEntity>.mapToEntity(): EndpointOverrideEntity {
        return EndpointOverrideEntity(
            id = id,
            Long = Long!!,
            Long = Long!!,
            overrideName = overrideName,
            description = description,
            isEnabled = isEnabled,
            isExpanded = isExpanded,
            isHorizontalDisabled = isHorizontalDisabled!!
        )
    }

}