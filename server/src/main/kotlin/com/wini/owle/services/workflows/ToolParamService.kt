package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.ToolParam
import com.wini.owle.entity.workflows.ToolParamEntity
import com.wini.owle.mapper.workflows.ToolParamsMapper
import com.wini.owle.repo.ToolParamIdFactory
import com.wini.owle.repo.workflows.ToolParamRepository
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