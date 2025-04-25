package com.wini.owle.dto.runs.test

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.GenerationMethod
import kotlinx.serialization.Serializable

@Serializable
data class TestInput(
    override val id: Long,
    val testCase: TestCase,
    val dataPointName: String,
    val value: String?,
    val generationMethod: GenerationMethod
) : StandardDto