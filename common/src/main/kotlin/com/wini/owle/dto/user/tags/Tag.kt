package com.wini.owle.dto.user.tags

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow.Companion.MAX_NAME_LENGTH
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable


@ModelDefinition
@Serializable
data class Tag(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,

    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val description: String? = null,

    val user: User

) : StandardDto