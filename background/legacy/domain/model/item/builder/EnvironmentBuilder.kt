package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.environment.EnvironmentEntity
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveEnvironment
import com.wini.owle.air.domain.cal.CalOverrideAccessor
import com.wini.owle.air.domain.cal.Calibration
import com.wini.owle.air.domain.environment.EnvironmentAccessor
import com.wini.owle.air.domain.model.item.CalOverride
import com.wini.owle.air.domain.model.item.Environment
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.inlines.*
import kotlinx.coroutines.flow.single

object EnvironmentBuilder : LoItem.LoItemBuilder<EnvironmentEntity> {

    var id: EnvironmentId by mutableStateOf(EnvironmentId(-1))
    var name: Name? by mutableStateOf(null)
    var description: Description? by mutableStateOf(null)
    var isEnabled: IsEnabled by mutableStateOf(IsEnabled(false))
    var isExpanded: IsExpanded by mutableStateOf(IsExpanded(false))
    var isHorizontalDisabled: IsHorizontalDisabled? by mutableStateOf(null)

    operator fun invoke(environment: Environment, isCloneItem: Boolean): EnvironmentBuilder = apply {
        id = if (!isCloneItem) environment.id else id
        name = environment.name
        description = environment.description
        isEnabled = environment.isEnabled
        isHorizontalDisabled = environment.isHorizontalDisable
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            name != null && isHorizontalDisabled != null
        )

    override fun build(): SaveEnvironment {
        return if (isValid.value) SaveEnvironment(mapToEntity()) else error("")
    }

    override fun LoItem.LoItemBuilder<EnvironmentEntity>.mapToEntity(): EnvironmentEntity {
        return EnvironmentEntity(
            id,
            name!!,
            description,
            isEnabled,
            isExpanded,
            isHorizontalDisabled!!
        )
    }

}