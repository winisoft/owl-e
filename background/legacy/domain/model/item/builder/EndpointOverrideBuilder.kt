package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.endpoint.EndpointOverrideEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveEndpointOverride
import com.wini.owle.air.domain.model.item.EndpointOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.inlines.*
import kotlin.reflect.KProperty

object EndpointOverrideBuilder : LoItem.LoItemBuilder<EndpointOverrideEntity> {

    var id: EndpointOverrideId = EndpointOverrideId(-1)
    var apiOverrideId: ApiOverrideId? = null
    var endpointId: EndpointId? by mutableStateOf(null)
    var overrideName: OverrideName? = null
    var description: Description? = null
    var isEnabled: IsEnabled by mutableStateOf(IsEnabled(false))
    var isExpanded: IsExpanded by mutableStateOf(IsExpanded(false))
    var isHorizontalDisabled: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(apiOverrideId: ApiOverrideId): EndpointOverrideBuilder = apply {
        this.apiOverrideId = apiOverrideId
    }

    operator fun invoke(endpointOverride: EndpointOverride): EndpointOverrideBuilder = apply {
        id = endpointOverride.id
        apiOverrideId = endpointOverride.apiOverrideId
        endpointId = endpointOverride.endpointId
        overrideName = endpointOverride.overrideName
        description = endpointOverride.description
        isEnabled = endpointOverride.isEnabled
        isExpanded = endpointOverride.isExpanded
        isHorizontalDisabled = endpointOverride.isHorizontalDisable
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            apiOverrideId != null &&
                  endpointId != null &&
                  apiOverrideId != null &&
                  isHorizontalDisabled != null
            )


    override fun build(): SaveEndpointOverride {
        return if (isValid.value) SaveEndpointOverride(mapToEntity()) else error("")
    }

    override fun LoItem.LoItemBuilder<EndpointOverrideEntity>.mapToEntity(): EndpointOverrideEntity {
        return EndpointOverrideEntity(
            id = id,
            endpointId = endpointId!!,
            apiOverrideId = apiOverrideId!!,
            overrideName = overrideName,
            description = description,
            isEnabled = isEnabled,
            isExpanded = isExpanded,
            isHorizontalDisabled = isHorizontalDisabled!!
        )
    }

}