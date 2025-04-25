package com.wini.owle.services.steps

import com.wini.owle.dto.steps.InitialDataPointInjector
import com.wini.owle.entity.steps.InitialDataPointInjectorEntity
import com.wini.owle.mapper.steps.PreReqInjectionsMapper
import com.wini.owle.repo.PreReqInjectionIdFactory
import com.wini.owle.repo.steps.PreReqInjectionRepository
import com.wini.owle.services.BaseServiceImpl


class PreReqInjectionService(
    repository: PreReqInjectionRepository,
    mapper: PreReqInjectionsMapper,
    entityIdFactory: PreReqInjectionIdFactory
): BaseServiceImpl<Long, InitialDataPointInjector, InitialDataPointInjectorEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)