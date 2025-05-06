package com.wini.owle.dto.steps

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.DataPoint
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class InitialDataPointInjector(

    @LongValueMin(0)
    override val id: Long,

    val junction: WorkflowJunction,

    val sourceDataPoint: DataPoint,

    val injectedDataPoint: DataPoint,

    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String?,

    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_DESC_LENGTH)
    val description: String?

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 512
        const val MAX_DESC_LENGTH = 1024
    }
}