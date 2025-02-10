package com.wini.owle.entity.typing

import com.wini.owle.entity.TypeSchemaRefs
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a reference within a type schema, supporting nested or hierarchical type structures.
 * Each schema reference is tied to a specific type declaration and may contain child references.
 */
class TypeSchemaRefDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TypeSchemaRefDao>(TypeSchemaRefs)

    /**
     * Reference to the parent schema, allowing nested or recursive schemas.
     */
    var parentRef by TypeSchemaRefDao optionalReferencedOn TypeSchemaRefs.parentRef

    /**
     * The name of the property or field within the schema.
     */
    var name by TypeSchemaRefs.name

    /**
     * Type declaration associated with this schema reference.
     */
    var typeDeclaration by TypeDeclarationDao referencedOn TypeSchemaRefs.typeDeclaration

    /**
     * Optional description providing details on the schema reference's purpose.
     */
    var description by TypeSchemaRefs.description
}