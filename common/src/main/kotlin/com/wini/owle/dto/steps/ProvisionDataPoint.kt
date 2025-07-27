package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class ProvisionDataPoint(

    @ConstraintSimple([PositiveValueLongError::class])
    val provisionId: Long,

    @ConstraintSimple([PositiveValueLongError::class])
    val dataPointId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "provisionId=$provisionId,dataPointId=$dataPointId"
    }
}