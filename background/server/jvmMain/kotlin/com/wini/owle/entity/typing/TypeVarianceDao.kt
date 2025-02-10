package com.wini.owle.entity.typing

import com.wini.owle.entity.TypeVariance
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
class TypeVarianceDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TypeVarianceDao>(TypeVariance)

    /**
     * Relationship to the type variable declaration.
     */
    var typeVariableDeclaration by TypeDeclarationDao referencedOn TypeVariance.typeVariableDeclaration

    /**
     * Specifies the type variance: covariant, contravariant, or invariant.
     */
    var varianceCardinality by TypeVariance.variance
}