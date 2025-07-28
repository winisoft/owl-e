package com.wini.owle.dto.steps

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.PositiveValueInt
import com.wini.owle.schema.error.StringNotEmpty
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