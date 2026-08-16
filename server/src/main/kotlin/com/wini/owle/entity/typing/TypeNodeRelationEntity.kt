package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * An edge between two type nodes. [relationKind] distinguishes a genuine MRO base-class edge
 * from a generic-parameterization edge, which the graph walk has to treat differently.
 */
class TypeNodeRelationEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TypeNodeRelationEntity>(TypeNodeRelations)

    /**
     * The parent node in the relationship.
     */
    var parentNode by TypeNodeEntity referencedOn TypeNodeRelations.parentNode

    /**
     * The child node in the relationship.
     */
    var childNode by TypeNodeEntity referencedOn TypeNodeRelations.childNode

    /**
     * `inheritance` or `instantiation`.
     */
    var relationKind by TypeNodeRelations.relationKind
}
