package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.typing.enums.VarianceCardinality
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
@ModelDefinition
@ApiEndpointDefinition("api.typing.typeVariance", ["type"], "/type-variance")
@Serializable
data class TypeVarVariance(

    /**
     * Relationship to the type variable declaration.
     */
    override val id: ULong,

    val typeVarNode: TypeNode,

    /**
     * Specifies the type variance: covariant, contravariant, or invariant.
     */
    val varianceCardinality: VarianceCardinality

) : ULongDto
