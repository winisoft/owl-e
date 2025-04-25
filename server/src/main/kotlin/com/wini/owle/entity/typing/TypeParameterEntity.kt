package com.wini.owle.entity.typing

import com.wini.owle.entity.TypeParameters
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents parameters used within complex types, allowing for parameterized type declarations.
 * Supports types such as 'List[int]' or 'Dict[str, int]', where 'int' or 'str' would be parameters of 'List' or 'Dict'.
 */
class TypeParameterEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TypeParameterEntity>(TypeParameters)

    /**
     * Relationship to the type declaration this parameter is part of.
     */
    var typeDeclarationEntity by TypeDeclarationEntity referencedOn TypeParameters.typeDeclaration

    /**
     * Position of the parameter within the type declaration, useful for ordered parameters (e.g., Dict[keyType, valueType]).
     */
    var position by TypeParameters.position

    /**
     * Relationship to the type declaration that defines the parameter’s type.
     */
    var parameterTypeDeclarationEntity by TypeDeclarationEntity referencedOn TypeParameters.parameterTypeDeclaration
}