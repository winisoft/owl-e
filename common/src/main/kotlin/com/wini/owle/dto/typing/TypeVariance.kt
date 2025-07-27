package com.wini.owle.dto.typing

import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.enums.VarianceCardinalityDto
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.schema.error.ConstraintSimple
import kotlinx.serialization.Serializable

/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
@ModelDefinition
@Serializable
data class TypeVariance(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Relationship to the type variable declaration.
     */
    val typeVariableDeclaration: TypeDeclaration,

    /**
     * Specifies the type variance: covariant, contravariant, or invariant.
     */
    val varianceCardinality: VarianceCardinalityDto
) : StandardDto