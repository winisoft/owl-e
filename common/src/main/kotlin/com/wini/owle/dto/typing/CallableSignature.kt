package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import kotlinx.serialization.Serializable


@Serializable
data class CallableSignature(

    override val id: ULong,

    val parentSchemaRef : TypeSchemaRef,

    val returnTypeNode : TypeSchemaRef,

    val name : String?,

    val docstring : String,

    val isStatic : Boolean,

    val isClassmethod : Boolean

): ULongDto