package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents parameters used within complex types, allowing for parameterized type declarations.
 * Supports types such as 'List\[int]' or 'Dict[str, int]', where 'int' or 'str' would be parameters of 'List' or 'Dict'.
 */
class TypeParameterEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeParameterEntity>(TypeParameters)

    /**
     * Relationship to the type declaration this parameter is part of.
     */
    var typeNode by TypeNodeEntity referencedOn TypeParameters.typeDeclaration

    /**
     * Position of the parameter within the type declaration, useful for ordered parameters (e.g., Dict[keyType, valueType]).
     */
    var position by TypeParameters.position

    /**
     * Relationship to the type declaration that defines the parameter’s type.
     */
    var parameterTypeNode by TypeNodeEntity referencedOn TypeParameters.parameterTypeDeclaration
}