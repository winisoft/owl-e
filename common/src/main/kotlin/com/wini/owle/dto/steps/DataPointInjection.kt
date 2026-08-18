package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 *
 */
@ModelDefinition
@ApiEndpointDefinition("api.steps.dataPointInjection", ["step"], "/datapoint-injection")
@Serializable
data class DataPointInjection(

    override val id: ULong,

    val junction: WorkflowJunction,

    val sourceDataPoint: DataPoint,

    val injectedDataPoint: DataPoint,

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DataPointInjection.NAME_MAX_LENGTH)
    val name: String?,

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.DataPointInjection.DESC_MAX_LENGTH)
    val description: String?

) : ULongDto