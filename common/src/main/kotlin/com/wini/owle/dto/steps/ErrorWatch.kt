package com.wini.owle.dto.steps

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Step
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class ErrorWatch(

    @LongValueMin(0)
    override val id: Long,

    val step: Step,

    val errorType: String,

    val triggerStep: Step

) : StandardDto