package com.wini.owle.dto.typing

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable


@Serializable
data class TypeVarConstraint(

    /**
     * The TypeVar's own TypeNode
     */
    val reference : TypeNode,

    /**
     * A TypeNode reference to the bound, e.g. 'int' or 'str'
     */
    val constraint : TypeNode

) : CompositeDto {

    override val owleId: String get() = "reference=${reference.owleId},constraint=${constraint.owleId}"

}
