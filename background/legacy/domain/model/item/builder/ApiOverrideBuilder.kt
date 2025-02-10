package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.api.ApiOverrideEntity
import com.wini.owle.air.domain.action.SaveApiOverride
import com.wini.owle.air.domain.api.Api
import com.wini.owle.air.domain.api.ApiOverrideAccessor
import com.wini.owle.air.domain.model.item.ApiOverride
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.inlines.*


object ApiOverrideBuilder : LoItem.LoItemBuilder<ApiOverrideEntity> {

    var id: ApiOverrideId? = ApiOverrideId(-1)
    var apiId: ApiId? by mutableStateOf(null)
    var environmentId: EnvironmentId? by mutableStateOf(null)
    var overrideName: OverrideName? by mutableStateOf(null)
    var description: Description? by mutableStateOf(null)
    var isEnabled: IsEnabled by mutableStateOf(IsEnabled(false))
    var isExpanded: IsExpanded by mutableStateOf(IsExpanded(false))
    var isHorizontalDisabled: IsHorizontalDisabled by mutableStateOf(IsHorizontalDisabled(isEnabled.value))

    operator fun invoke(apiOverride: ApiOverride): ApiOverrideBuilder = apply {
        id = apiOverride.id
        apiId = apiOverride.apiId
        environmentId = apiOverride.environmentId
        overrideName = apiOverride.overrideName
        description = apiOverride.description
        isEnabled = apiOverride.isEnabled
        isExpanded = apiOverride.isExpanded
        isHorizontalDisabled = apiOverride.isHorizontalDisable
    }

    operator fun invoke(
        envId: EnvironmentId
    ): ApiOverrideBuilder = apply {
        environmentId = envId
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            id != null &&
                apiId != null &&
                environmentId != null
        )

    override fun build(): SaveApiOverride {
        return SaveApiOverride(mapToEntity())
    }

    override fun LoItem.LoItemBuilder<ApiOverrideEntity>.mapToEntity(): ApiOverrideEntity =
        ApiOverrideEntity(
            id = id!!,
            apiId = apiId!!,
            environmentId = environmentId!!,
            overrideName = overrideName,
            description = description,
            isEnabled = isEnabled,
            isExpanded = isExpanded,
            isHorizontalDisabled = isHorizontalDisabled
        )

}