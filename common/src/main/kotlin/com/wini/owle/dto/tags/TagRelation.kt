package com.wini.owle.dto.tags

import com.wini.owle.dto.CompositeDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class TagRelation(

    @ConstraintSimple([PositiveValueULongError::class])
    val parentTagId: ULong,

    @ConstraintSimple([PositiveValueULongError::class])
    val childTagId: ULong
) : CompositeDto {

    override val owleId: String get() = "parentTagId=$parentTagId,childTagId=$childTagId"
}