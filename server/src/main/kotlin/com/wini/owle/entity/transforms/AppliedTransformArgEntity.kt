package com.wini.owle.entity.transforms

import com.wini.owle.entity.AppliedTransformArgs
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class AppliedTransformArgEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<AppliedTransformArgEntity>(AppliedTransformArgs)

    var appliedTransformEntity by AppliedTransformEntity referencedOn AppliedTransformArgs.appliedTransform

    var transformationArg by TransformArgEntity referencedOn AppliedTransformArgs.transformationArg

    var value by AppliedTransformArgs.value

}