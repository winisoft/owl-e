package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * A TypeVar declared by a generic class, in position order.
 */
@ModelDefinition
@Serializable
data class TypeGenericParam(

    override val id: ULong,

    val classNode: TypeNode,

    val typevarNode: TypeNode,

    val position: ULong,

) : ULongDto
