package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Resolves an alias node to what it actually names.
 */
@ModelDefinition
@Serializable
data class TypeAlias(

    override val id: ULong,

    /** The node being aliased; this DTO's own id is the alias node. */
    val target: TypeNode,

) : ULongDto
