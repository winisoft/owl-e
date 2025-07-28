package com.wini.owle.dto.steps

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.StringNotEmpty
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