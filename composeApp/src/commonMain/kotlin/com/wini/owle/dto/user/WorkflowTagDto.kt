package com.wini.owle.dto.user

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowTagDto(
    val workflowId: Long,
    val tagId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "workflowId={$workflowId}&tagId={$tagId}"
    }
}