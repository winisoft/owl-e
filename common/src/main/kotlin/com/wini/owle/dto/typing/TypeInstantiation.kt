package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * A generic applied to arguments: `List` (origin) becomes `List[int]` (instantiation).
 */
@ModelDefinition
@Serializable
data class TypeInstantiation(

    override val id: ULong,

    val instantiationNode: TypeNode,

    val originNode: TypeNode,

) : ULongDto
