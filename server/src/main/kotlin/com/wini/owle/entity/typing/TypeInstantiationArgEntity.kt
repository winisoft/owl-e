package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


class TypeInstantiationArgEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeInstantiationArgEntity>(TypeInstantiationArgs)

    var instantiation by TypeInstantiationEntity referencedOn TypeInstantiationArgs.instantiation

    var paramNode by TypeNodeEntity referencedOn TypeInstantiationArgs.paramNode

    var paramPosition by TypeInstantiationArgs.paramPosition
}
