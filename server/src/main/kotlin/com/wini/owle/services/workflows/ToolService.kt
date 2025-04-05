package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.ToolDto
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.mapper.workflows.ToolsMapper
import com.wini.owle.repo.ToolIdFactory
import com.wini.owle.repo.workflows.ToolRepository
import com.wini.owle.services.BaseServiceImpl


class ToolService(
    repository: ToolRepository,
    mapper: ToolsMapper,
    entityIdFactory: ToolIdFactory
): BaseServiceImpl<Long, ToolDto, ToolDao>(
    repository,
    mapper,
    entityIdFactory
)