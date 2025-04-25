package com.wini.owle.entity.typing

import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.environs.VersionSpecEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Defines the core types in the system, such as 'int', 'str', 'list', 'dict', 'Union', etc.
 * These are foundational definitions that are later instantiated or parameterized in type declarations.
 */
class TypeDefinitionEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TypeDefinitionEntity>(TypeDefinitions)

    /**
     * Name of the type, e.g., 'int', 'str', 'list'.
     */
    var name by TypeDefinitions.name

    /**
     * Namespace that specifies where this type is defined, e.g., 'builtins' or 'collections'.
     */
    var namespace by TypeDefinitions.namespace

    /**
     * Relationship to the version specification.
     */
    var versionSpec by VersionSpecEntity referencedOn TypeDefinitions
}