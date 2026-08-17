package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * One positional argument of a generic instantiation.
 */
@ModelDefinition
@Serializable
data class TypeInstantiationArg(

    override val id: ULong,

    val instantiation: TypeInstantiation,

    val paramNode: TypeNode,

    val paramPosition: UInt,

) : ULongDto
