package com.wini.owle.services.runs

import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.mapper.runs.StepExecutionsMapper
import com.wini.owle.repo.runs.StepExecutionRepository
import com.wini.owle.services.BaseServiceImpl


class StepExecutionService(
    repository: StepExecutionRepository,
    mapper: StepExecutionsMapper
): BaseServiceImpl<Long, StepExecutionDto, StepExecutionDao>(repository, mapper)