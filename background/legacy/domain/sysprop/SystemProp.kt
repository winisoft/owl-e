package com.wini.owle.air.domain.sysprop

import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.inlines.Description
import com.wini.owle.air.inlines.SysPropKey
import com.wini.owle.air.inlines.SystemPropId

data class SystemProp(
    val id: SystemPropId,
    val key: SysPropKey,
    val dataType: DataType,
    val description: Description?
)