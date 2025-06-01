package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
class TypeVarVarianceEntity(id: EntityID<CompositeID>) :  CompositeEntity(id) {

    companion object : CompositeEntityClass<TypeVarVarianceEntity>(TypeVarVariances)

    /**
     * Each TypeVar can only have one variance row
     */
    var typeVarNode by TypeNodeEntity referencedOn TypeVarBounds.typeVarNode


    var varianceCardinality by TypeVarVariances.variance

}
