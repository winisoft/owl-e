package com.wini.owle.dto.typing

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.enums.VarianceCardinalityDto
import kotlinx.serialization.Serializable

/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
@OwleModel
@Serializable
data class TypeVariance(

    @LongValueMin(0)
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