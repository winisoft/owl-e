package com.wini.owle.entity.typing

import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.transforms.TransformTargetTypes
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID

/**
 *
 */
class TypeVarConstraintEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TypeVarConstraintEntity>(TypeVarConstraints)

    /**
     * The TypeVar's own TypeNode
     */
    var typeVarNode by TypeNodeEntity referencedOn TypeVarConstraints.typeVarNode

    /**
     * A TypeNode reference to the bound, e.g. 'int' or 'str'
     */
    var constraintNode by TypeNodeEntity referencedOn TypeVarConstraints.constraintNode


}

