package com.wini.owle.dto.user.tags

import com.wini.owle.ksp.OwleModel
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class StepTag(
    val stepId: Long,
    val tagId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "stepId=$stepId,tagId=$tagId"
    }
}