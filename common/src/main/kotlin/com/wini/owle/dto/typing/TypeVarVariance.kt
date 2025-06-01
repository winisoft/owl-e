package com.wini.owle.dto.typing

import com.wini.owle.dto.CompositeDto
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.typing.enums.VarianceCardinality
import kotlinx.serialization.Serializable


/**
 * Represents variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.
 * Variance annotations define how types relate to their subtypes in generic contexts.
 */
@ModelDefinition
@Serializable
data class TypeVarVariance(

    /**
     * Relationship to the type variable declaration.
     */
    val typeNode: TypeNode,

    /**
     * Specifies the type variance: covariant, contravariant, or invariant.
     */
    val cardinality: VarianceCardinality

) : CompositeDto {

    override val owleId: String get() = "typeNode=${typeNode.owleId},cardinality=${cardinality.name}"

}
