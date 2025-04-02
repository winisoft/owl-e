package com.wini.owle.entity.transforms

import com.wini.owle.entity.AppliedTransformArgs
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class AppliedTransformArgDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<AppliedTransformArgDao>(AppliedTransformArgs)

    var appliedTransform by AppliedTransformDao referencedOn AppliedTransformArgs.appliedTransform

    var transformationArg by TransformationArgDao referencedOn AppliedTransformArgs.transformationArg

    var value by AppliedTransformArgs.value

}