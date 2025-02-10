package com.wini.owle.mapper.runs

import com.wini.owle.dto.WorkflowExecutionDto
import com.wini.owle.entity.runs.WorkflowExecutionEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class WorkflowExecutionMapper : BaseDbMapper<WorkflowExecutionDto, WorkflowExecutionEntity>() {

    }