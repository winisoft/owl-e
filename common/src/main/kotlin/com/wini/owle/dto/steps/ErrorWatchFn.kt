package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


@ModelDefinition
@ApiEndpointDefinition("api.steps.errorWatchFn", ["step"], "/error-watch-fn")
@Serializable
data class ErrorWatchFn(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    val errorWatch: ErrorWatch,

    val functionNode: TypeNode,

    @ConstraintSimple([PositiveValueULongError::class])
    val position: ULong

) : ULongDto