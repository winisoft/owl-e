package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


@ModelDefinition
@ApiEndpointDefinition("api.typing.callableParameterApi", ["type"], "/callable-parameter")
@Serializable
data class CallableParameter(

    override val id: ULong,

    /**
     * A callable signature this parameter exists for
     */
    val signature: CallableSignature,

    /**
     * The TypeNode of this parameter's type
     */
    val paramTypeNode: TypeNode,

    /**
     * A 0-based index of parameter positions
     */
    val position: UInt,

    /**
     *  e.g. 'x', 'y', 'flags'
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.CallableParameter.PARAM_NAME_MAX_LENGTH)
    val paramName: String,

    /**
     * if *args
     */
    val isVarArg: Boolean,

    /**
     * if **kwargs
     */
    val isKwarg: Boolean,

    /**
     * default literal text, if any
     */
    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.CallableParameter.DEFAULT_VALUE_TEXT_MAX_LENGTH)
    val defaultValueText: String?

) : ULongDto {

    override val owleId: String get() = ""


}

