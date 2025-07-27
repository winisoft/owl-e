package com.wini.owle.dto.user.tags

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class TagRelation(

    @ConstraintSimple([PositiveValueLongError::class])
    val parentTagId: Long,

    @ConstraintSimple([PositiveValueLongError::class])
    val childTagId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "parentTagId=$parentTagId,childTagId=$childTagId"
    }
}