package com.wini.owle.mapper.runs

import com.wini.owle.dto.ExecutionLogDto
import com.wini.owle.entity.runs.ExecutionLogEntity
import com.wini.owle.mapper.BaseDbMapper

sealed class ExecutionLogMapper : BaseDbMapper<ExecutionLogDto, ExecutionLogEntity>() {

    }