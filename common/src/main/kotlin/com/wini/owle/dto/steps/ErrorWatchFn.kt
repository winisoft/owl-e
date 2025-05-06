package com.wini.owle.dto.steps

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class ErrorWatchFn(

    @LongValueMin(0)
    override val id: Long,

    val errorWatch: ErrorWatch,

    @StringLengthMin(1)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_FN_NAME_LENGTH)
    val functionName: String,

    @LongValueMin(0)
    val position: Int

) : StandardDto {

    companion object {
        const val MAX_FN_NAME_LENGTH = 255
    }
}