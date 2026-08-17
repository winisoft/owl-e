package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


@Serializable
@ApiEndpointDefinition("api.typing.callableSignatureApi", ["type"], "/callable-signature")
data class CallableSignature(

    override val id: ULong,

    val parentSchemaRef : TypeSchemaRef,

    val returnTypeNode : TypeSchemaRef,

    val name : String?,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.CallableSignature.DOCSTRING_MAX_LENGTH)
    val docstring : String,

    val isStatic : Boolean,

    val isClassmethod : Boolean

): ULongDto