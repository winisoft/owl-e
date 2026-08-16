package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Binds a callable node to the class node that declares it. Kept as its own edge rather than a
 * column on the callable so an unbound function is simply a callable with no owner row.
 */
class CallableOwnerEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<CallableOwnerEntity>(CallableOwners)

    /**
     * The declaring class node.
     */
    var ownerNode by TypeNodeEntity referencedOn CallableOwners.ownerNode

    /**
     * The callable node being declared.
     */
    var callableNode by TypeNodeEntity referencedOn CallableOwners.callableNode
}
