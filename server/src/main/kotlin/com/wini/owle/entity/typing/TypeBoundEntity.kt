package com.wini.owle.entity.typing

import com.wini.owle.entity.TypeBounds
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents bounds on type variables, supporting constraints like 'T: int' for generic types.
 * Allows capturing constraints on type variables in complex type declarations.
 */
class TypeBoundEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TypeBoundEntity>(TypeBounds)

    /**
     * Relationship to the type variable declaration.
     */
    var typeVariableDeclaration by TypeDeclarationEntity referencedOn TypeBounds.typeVariableDeclaration

    /**
     * Relationship to the bound type declaration.
     */
    var boundTypeDeclarationEntity by TypeDeclarationEntity referencedOn TypeBounds.boundTypeDeclaration
}