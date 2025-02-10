package com.wini.owle.air.domain.model.item.builder

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wini.owle.air.db.cal.CalOverrideEntity
import com.wini.owle.air.domain.action.SaveCalOverride
import com.wini.owle.air.domain.model.CalOverride
import com.wini.owle.air.domain.model.LoItem
import com.wini.owle.air.inlines.*

object CalOverrideBuilder : LoItem.LoItemBuilder<CalOverrideEntity> {

    var id: Long = Long(-1)
    var calId: Pair<() -> Boolean, String?> by mutableStateOf({ calId.second != null } to null)
    var Long: Pair<() -> Boolean, Long?> by mutableStateOf({ Long.second != null } to null)
    var overrideName: String? by mutableStateOf(null)
    var overrideValue: OverrideValue? by mutableStateOf(null)
    var description:String?? by mutableStateOf(null)
    var isEnabled: Boolean by mutableStateOf(Boolean(false))
    var isHorizontalDisabled: Pair<() -> Boolean, IsHorizontalDisabled?> by mutableStateOf({ isHorizontalDisabled.second != null } to null)

    operator fun invoke(calOverride: CalOverride): CalOverrideBuilder = apply {
        id = calOverride.id
        calId = calId.first to calOverride.calId
        Long = Long.first to calOverride.Long
        overrideName = overrideName
        overrideValue = overrideValue
        description = calOverride.description
        isEnabled = calOverride.isEnabled
        isHorizontalDisabled = isHorizontalDisabled.first to calOverride.isHorizontalDisable
    }

    operator fun invoke(envId: Long): CalOverrideBuilder = apply {
        Long = Long.first to envId
    }

    override val isValid: MutableState<Boolean>
        get() = mutableStateOf(
            isHorizontalDisabled.first() && calId.first() && Long.first()
        )

    override fun build(): SaveCalOverride = SaveCalOverride(
        if (isValid.value) mapToEntity() else error("The isValid field should inform you when it is safe to call this")
    )

    override fun LoItem.LoItemBuilder<CalOverrideEntity>.mapToEntity(): CalOverrideEntity {
        return CalOverrideEntity(
            id = id,
            calId = calId.second!!,
            Long = Long.second!!,
            overrideName = overrideName,
            overrideValue = overrideValue,
            description = description,
            isEnabled = isEnabled,
            isHorizontalDisabled = isHorizontalDisabled.second!!
        )
    }

}