package com.wini.owle.dto.steps

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable


@OwleModel
@Serializable
data class ProvisionDataPoint(
    @LongValueMin(0)
    val provisionId: Long,
    @LongValueMin(0)
    val dataPointId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "provisionId=$provisionId,dataPointId=$dataPointId"
    }
}