package com.wini.owle.entity.transforms

import com.wini.owle.entity.TransformationArgs
import com.wini.owle.entity.typing.TypeDeclarationEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class TransformArgEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TransformArgEntity>(TransformationArgs)

    var transformation by TransformEntity referencedOn TransformationArgs.transformation

    var typeDeclarationEntity by TypeDeclarationEntity referencedOn TransformationArgs.typeDeclaration

    var name by TransformationArgs.name

    var description by TransformationArgs.description

}
