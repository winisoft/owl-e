package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class ErrorWatchFnDto(
    override val id: Long,
    val errorWatch: ErrorWatchDto,
    val functionName: String,
    val position: Int
) : StandardDto