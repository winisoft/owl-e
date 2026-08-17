package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition
import com.wini.owle.schema.ModelDefinition


@ModelDefinition
@Serializable
@ApiEndpointDefinition("api.typing.typeVarConstraint", ["type"], "/type-var-constraint")
data class TypeVarConstraint(

    /**
     * The TypeVar's own TypeNode
     */
    override val id: ULong,

    val typeVarNode : TypeNode,

    /**
     * A TypeNode reference to the bound, e.g. 'int' or 'str'
     */
    val constraintNode : TypeNode

) : ULongDto
