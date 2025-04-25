package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.Tool
import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.mapper.workflows.ToolsMapper
import com.wini.owle.repo.ToolIdFactory
import com.wini.owle.repo.workflows.ToolRepository
import com.wini.owle.services.BaseServiceImpl


class ToolService(
    repository: ToolRepository,
    mapper: ToolsMapper,
    entityIdFactory: ToolIdFactory
): BaseServiceImpl<Long, Tool, ToolEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)