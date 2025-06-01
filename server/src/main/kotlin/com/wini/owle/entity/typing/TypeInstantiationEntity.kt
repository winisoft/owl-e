package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass

class TypeInstantiationEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeInstantiationEntity>(TypeInstantiations)

    var instantiationNode by TypeNodeEntity referencedOn TypeInstantiations.instantiation

    var originNode by TypeNodeEntity referencedOn TypeInstantiations.origin

}