package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TestInputDto(
    val id: Long,
    val testCase: TestCaseDto,
    val dataPointName: String,
    val value: String?,
    val generationMethod: GenerationMethodDto
) : BaseDto