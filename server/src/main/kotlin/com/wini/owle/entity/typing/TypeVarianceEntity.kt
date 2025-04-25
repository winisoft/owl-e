package com.wini.owle.entity.typing

import com.wini.owle.entity.TypeVariances
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
class TypeVarianceEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TypeVarianceEntity>(TypeVariances)

    /**
     * Relationship to the type variable declaration.
     */
    var typeVariableDeclaration by TypeDeclarationEntity referencedOn TypeVariances.typeVariableDeclaration

    /**
     * Specifies the type variance: covariant, contravariant, or invariant.
     */
    var varianceCardinality by TypeVariances.variance
}