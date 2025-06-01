package com.wini.owle.entity.steps

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID

class DataPointReadEntity(id: EntityID<ULong>): ULongEntity(id) {

    companion object : ULongEntityClass<DataPointReadEntity>(DataPointReads)


}