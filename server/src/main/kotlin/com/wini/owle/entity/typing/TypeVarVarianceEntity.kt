package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
class TypeVarVarianceEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeVarVarianceEntity>(TypeVarVariances)

    /**
     * Each TypeVar can only have one variance row — the column carries a unique index.
     */
    var typeVarNode by TypeNodeEntity referencedOn TypeVarVariances.typeVarNode

    var varianceCardinality by TypeVarVariances.variance
}
