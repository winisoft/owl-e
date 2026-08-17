package com.wini.owle.dto.steps

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * One positional argument to a write-site transform.
 */
@ModelDefinition
@Serializable
data class DataPointWriteArg(

    val dataPointWrite: DataPointWrite,

    val dataPoint: DataPoint,

    val position: UInt,

) : CompositeDto {

    override val owleId: String get() = "dataPointWrite=${dataPointWrite.owleId},position=$position"
}
