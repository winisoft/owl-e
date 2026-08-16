package com.wini.owle.entity.transforms

import com.wini.owle.entity.typing.CallableSignatureEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A reusable value transformation. Applied at a specific site — a read, a write, an inquisition
 * argument, or a junction injection — by a [TransformOccurrenceEntity].
 */
class TransformEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TransformEntity>(Transforms)

    /**
     * Name of the transformation.
     */
    var name by Transforms.name

    /**
     * Detailed description of what the transformation does.
     */
    var description by Transforms.description

    /**
     * Optional examples to demonstrate the transformation's usage.
     */
    var examples by Transforms.examples

    /**
     * Typed call contract, when one has been resolved for this transform.
     */
    var signature by CallableSignatureEntity optionalReferencedOn Transforms.signature

    /**
     * Declared arguments this transform accepts.
     */
    val args: SizedIterable<TransformArgEntity> by TransformArgEntity referrersOn TransformArgs.transform

    /**
     * Applicability rows: which types this transform accepts, and what each yields.
     */
    val targetTypes: SizedIterable<TransformTargetTypeEntity> by TransformTargetTypeEntity referrersOn TransformTargetTypes.transform

    /**
     * The types for which this transformation can be applied.
     */
    val eligibleTypes: SizedIterable<TypeNodeEntity>
            by TypeNodeEntity.via(TransformTargetTypes.transform, TransformTargetTypes.eligibleTypeNode)

    /**
     * Every site at which this transform is currently applied.
     */
    val occurrences: SizedIterable<TransformOccurrenceEntity> by TransformOccurrenceEntity referrersOn TransformOccurrences.transform
}
