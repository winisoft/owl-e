package com.wini.owle.services.steps

import com.wini.owle.dto.steps.InquisitionDto
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.mapper.steps.InquisitionsMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.InquisitionIdFactory
import com.wini.owle.repo.steps.InquisitionRepository
import com.wini.owle.services.BaseServiceImpl


class InquisitionService(
    repository: InquisitionRepository,
    mapper: InquisitionsMapper,
    entityIdFactory: InquisitionIdFactory
): BaseServiceImpl<Long, InquisitionDto, InquisitionDao>(
    repository,
    mapper,
    entityIdFactory
)