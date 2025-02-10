package com.wini.owle.entity.transforms

import com.wini.owle.entity.OwleEntity
import com.wini.owle.entity.TransformationArgs
import com.wini.owle.entity.typing.TypeDeclarationDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class TransformationArgDao(id: EntityID<Long>) : LongEntity(id), OwleEntity<TransformationArgDao> {
    companion object : LongEntityClass<TransformationArgDao>(TransformationArgs)

    var transformation by TransformationDao referencedOn TransformationArgs.transformation

    var typeDeclaration by TypeDeclarationDao referencedOn TransformationArgs.typeDeclaration

    var name by TransformationArgs.name

    var description by TransformationArgs.description

}
