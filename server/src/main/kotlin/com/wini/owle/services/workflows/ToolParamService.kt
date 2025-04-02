package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.ToolParamDto
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.mapper.workflows.ToolParamsMapper
import com.wini.owle.repo.workflows.ToolParamRepository
import com.wini.owle.services.BaseServiceImpl


class ToolParamService(
    repository: ToolParamRepository,
    mapper: ToolParamsMapper
): BaseServiceImpl<Long, ToolParamDto, ToolParamDao>(repository, mapper)