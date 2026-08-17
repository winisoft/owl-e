package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition
import com.wini.owle.schema.ModelDefinition


@ModelDefinition
@Serializable
@ApiEndpointDefinition("api.typing.callableSignatureApi", ["type"], "/callable-signature")
data class CallableSignature(

    override val id: ULong,

    val typeNode : TypeNode,

    val returnTypeNode : TypeNode,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.CallableSignature.DOCSTRING_MAX_LENGTH)
    val docstring : String?,

    val isStatic : Boolean,

    val isClassmethod : Boolean

): ULongDto