package com.wini.owle.dto.steps

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable


@Serializable
data class ProvisionDataPointDto(
    val provisionId: Long,
    val dataPointId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "provisionId={$provisionId}&dataPointId={$dataPointId}"
    }
}