package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class ErrorWatchFn(
    override val id: Long,
    val errorWatch: ErrorWatch,
    val functionName: String,
    val position: Int
) : StandardDto