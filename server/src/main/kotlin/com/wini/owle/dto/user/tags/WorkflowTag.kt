package com.wini.owle.dto.user.tags

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowTag(
    val workflowId: Long,
    val tagId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "workflowId={$workflowId}&tagId={$tagId}"
    }
}