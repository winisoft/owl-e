package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


class TypeGenericParamEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeGenericParamEntity>(TypeGenericParams)

    var classNode by TypeNodeEntity referencedOn TypeGenericParams.classNode

    var typevarNode by TypeNodeEntity referencedOn TypeGenericParams.typeVar

    var position by TypeGenericParams.position

}