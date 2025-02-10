package com.wini.owle.mapper

import com.wini.owle.dto.BaseDto
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


interface CompositeIdMap<T: BaseDto> {
    fun buildId(dto: T): EntityID<CompositeID>
}