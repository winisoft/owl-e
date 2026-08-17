package com.wini.owle.dto.tags

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.tags.enums.TagSource
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.schema.error.TagWithNonSourceUserIdError
import com.wini.owle.schema.error.TagWithUserSourceUserIdError
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition



@Serializable
@ModelDefinition
@ApiEndpointDefinition("api.user.tag", ["user"], "/tag")
@TagWithNonSourceUserIdError("userId", "source")
@TagWithUserSourceUserIdError("userId", "source")
data class Tag(
    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    @ConstraintSimple([StringContainsRestrictedChars::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Tag.NAME_MAX_LENGTH)
    val name: String,

    @ConstraintSimple([StringContainsRestrictedChars::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Tag.DESC_MAX_LENGTH)
    val description: String? = null,

    val source: TagSource? = null,

    val childTags: List<Tag>? = null

) : ULongDto