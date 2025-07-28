package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.mapper.steps.ToolParamsMapper
import com.wini.owle.repo.ToolParamIdFactory
import com.wini.owle.repo.steps.ToolParamRepository
import com.wini.owle.services.BaseServiceImpl

class ToolParamService(
    repository: ToolParamRepository,
    mapper: ToolParamsMapper,
    entityIdFactory: ToolParamIdFactory
): BaseServiceImpl<Long, ToolParam, ToolParamEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)