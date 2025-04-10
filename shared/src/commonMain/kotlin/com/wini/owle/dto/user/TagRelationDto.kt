package com.wini.owle.dto.user

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

@Serializable
data class TagRelationDto(
    val parentTagId: Long,
    val childTagId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "parentTagId={$parentTagId}&childTagId={$childTagId}"
    }
}