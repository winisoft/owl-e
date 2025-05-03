package com.wini.owle.dto.steps

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class ErrorWatchFn(

    @LongValueMin(0)
    override val id: Long,

    val errorWatch: ErrorWatch,

    @StringLengthMin(0)
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