package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Represents a reference within a type schema, supporting nested or hierarchical type structures.
 * Each schema reference is tied to a specific type declaration and may contain child references.
 */
class TypeSchemaRefEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeSchemaRefEntity>(TypeSchemaRefs)

    /**
     * Reference to the parent schema, allowing nested or recursive schemas.
     *
     * -- If NULL: this is the “root schema node” for a class (points at the class’s type_nodes.id).
     * -- If NOT NULL: points at another row in this same table.
     */

    /**
     * Nested members declared beneath this one.
     */

    /**
     * The type that declares this member.
     */
    var classNode by TypeNodeEntity referencedOn TypeSchemaRefs.classNode

    /**
     * The member’s name (field, method, property, or classvar).
     */
    var name by TypeSchemaRefs.name

    /**
     * Type declaration associated with this schema reference.
     *
     * -- If attribute/classvar/property: this is the type (type_nodes.id).
     * -- If method: NULL — the call contract is on the callable node's own signature.
     */
    var typeNode by TypeNodeEntity optionalReferencedOn TypeSchemaRefs.typeNode

    /**
     * Optional description providing details on the schema reference's purpose.
     */
    var description by TypeSchemaRefs.description

    var position by TypeSchemaRefs.position

    var membership by TypeSchemaRefs.membership
}
