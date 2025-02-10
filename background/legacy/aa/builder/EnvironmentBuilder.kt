package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.environment.EnvironmentEntity
import com.wini.owle.air.domain.aa.inlines.IsExpanded
import com.wini.owle.air.domain.action.SaveEnvironment
import com.wini.owle.air.domain.model.Environment
import com.wini.owle.air.domain.model.LoItem
import com.wini.owle.air.inlines.*

object EnvironmentBuilder : LoItem.LoItemBuilder<EnvironmentEntity> {

    var id: Long by mutableStateOf(Long(-1))
    var name:String? by mutableStateOf(null)
    var description:String?? by mutableStateOf(null)
    var isEnabled: Boolean by mutableStateOf(Boolean(false))
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