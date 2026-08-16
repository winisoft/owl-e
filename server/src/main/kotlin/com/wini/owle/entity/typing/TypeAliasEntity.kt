package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Resolves an alias node to what it actually names — `Vector = List[float]` is two type nodes and
 * one of these. The row's own id *is* the alias node's id, so the alias side needs no second column.
 */
class TypeAliasEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeAliasEntity>(TypeAliases)

    /**
     * The node doing the aliasing — same row id as this entity.
     */
    val aliasNode: TypeNodeEntity get() = TypeNodeEntity[id]

    /**
     * The node being aliased.
     */
    var target by TypeNodeEntity referencedOn TypeAliases.target
}
