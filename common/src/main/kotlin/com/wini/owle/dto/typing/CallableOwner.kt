package com.wini.owle.dto.typing

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Binds a callable node to the class node that declares it.
 */
@ModelDefinition
@Serializable
data class CallableOwner(

    val ownerNode: TypeNode,

    val callableNode: TypeNode,

) : CompositeDto {

    override val owleId: String get() = "ownerNode=${ownerNode.owleId},callableNode=${callableNode.owleId}"
}
