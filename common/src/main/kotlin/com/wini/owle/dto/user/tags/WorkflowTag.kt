package com.wini.owle.dto.user.tags

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class WorkflowTag(

    @ConstraintSimple([PositiveValueLongError::class])
    val workflowId: Long,

    @ConstraintSimple([PositiveValueLongError::class])
    val tagId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "workflowId=$workflowId,tagId=$tagId"
    }

}