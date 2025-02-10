package com.wini.owle.mapper.runs

import com.wini.owle.dto.StepExecutionDto
import com.wini.owle.entity.runs.StepExecutionEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class StepExecutionMapper : BaseDbMapper<StepExecutionDto, StepExecutionEntity>() {

    }