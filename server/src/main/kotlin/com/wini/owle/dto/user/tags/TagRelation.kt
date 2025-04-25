package com.wini.owle.dto.user.tags

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

@Serializable
data class TagRelation(
    val parentTagId: Long,
    val childTagId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "parentTagId={$parentTagId}&childTagId={$childTagId}"
    }
}