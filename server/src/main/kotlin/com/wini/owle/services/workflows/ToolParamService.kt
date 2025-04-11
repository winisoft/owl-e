package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.ToolParamDto
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.mapper.workflows.ToolParamsMapper
import com.wini.owle.repo.ToolParamIdFactory
import com.wini.owle.repo.workflows.ToolParamRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class ToolParamService(
    repository: ToolParamRepository,
    mapper: ToolParamsMapper,
    entityIdFactory: ToolParamIdFactory
): BaseServiceImpl<Long, ToolParamDto, ToolParamDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)