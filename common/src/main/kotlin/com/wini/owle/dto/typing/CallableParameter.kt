package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable


@ModelDefinition
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
    val paramName: String,

    /**
     * if *args
     */
    val isVarArg: Boolean,

    /**
     * if **kwargs
     */
    val isKArg: Boolean,

    /**
     * default literal text, if any
     */
    val defaultValueText: String

) : ULongDto {

    override val owleId: String get() = ""


}

