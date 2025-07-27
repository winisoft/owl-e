package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class ErrorWatchFn(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    val errorWatch: ErrorWatch,

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_FN_NAME_LENGTH)
    val functionName: String,

    @ConstraintSimple([PositiveValueIntError::class])
    val position: Int

) : StandardDto {

    companion object {
        const val MAX_FN_NAME_LENGTH = 255
    }
}