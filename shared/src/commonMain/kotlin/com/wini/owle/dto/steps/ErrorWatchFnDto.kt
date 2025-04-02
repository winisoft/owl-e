package com.wini.owle.dto.steps

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class ErrorWatchFnDto(
    val id: Long,
    val errorWatch: ErrorWatchDto,
    val functionName: String,
    val position: Int
) : BaseDto