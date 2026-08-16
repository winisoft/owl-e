package com.wini.owle.entity.transforms

import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


class TransformArgEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TransformArgEntity>(TransformArgs)

    var transform by TransformEntity referencedOn TransformArgs.transform

    var typeNode by TypeNodeEntity referencedOn TransformArgs.typeNode

    var name by TransformArgs.name

    var description by TransformArgs.description
}
