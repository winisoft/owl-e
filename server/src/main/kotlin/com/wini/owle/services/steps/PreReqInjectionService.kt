package com.wini.owle.services.steps

import com.wini.owle.dto.steps.PreReqInjectionDto
import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.mapper.steps.PreReqInjectionsMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.PreReqInjectionIdFactory
import com.wini.owle.repo.steps.PreReqInjectionRepository
import com.wini.owle.services.BaseServiceImpl


class PreReqInjectionService(
    repository: PreReqInjectionRepository,
    mapper: PreReqInjectionsMapper,
    entityIdFactory: PreReqInjectionIdFactory
): BaseServiceImpl<Long, PreReqInjectionDto, PreReqInjectionDao>(
    repository,
    mapper,
    entityIdFactory
)