package com.wini.owle.dto.tags

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.schema.error.TagWithNonSourceUserIdError
import com.wini.owle.schema.error.TagWithUserSourceUserIdError
import kotlinx.serialization.Serializable



@Serializable
@ModelDefinition
@TagWithNonSourceUserIdError("userId", "source")
@TagWithUserSourceUserIdError("userId", "source")
data class Tag(
    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_255)
    val name: String,

    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_16384)
    val description: String? = null,

    val source: TagSource,

    val parentTag: Tag? = null,

    val childTags: List<Tag>? = null

) : ULongDto