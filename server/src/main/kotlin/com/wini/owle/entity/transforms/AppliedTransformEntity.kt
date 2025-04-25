package com.wini.owle.entity.transforms

import com.wini.owle.entity.AppliedTransforms
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.typing.TypeDeclarationEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents an instance of an applied transformation.
 * Each applied transformation has a type, may recursively reference a previous applied transform,
 * and specifies the result type it produces.
 */
class AppliedTransformEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<AppliedTransformEntity>(AppliedTransforms)

    /**
     * The transformation from which this applied instance originates.
     */
    var transformation by TransformEntity referencedOn AppliedTransforms.transformation

    /**
     * Describes the type of occurrence for the transformation (e.g., 'provision', 'revision').
     */
    var occurrence by AppliedTransforms.occurrence

    /**
     * The type of the result produced by this transformation.
     */
    var resultType by TypeDeclarationEntity referencedOn AppliedTransforms.resultTypeDeclaration

    /**
     * The data point associated with this applied transformation.
     */
    var dataPointEntity by DataPointEntity optionalReferencedOn AppliedTransforms.dataPoint

}