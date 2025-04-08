package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ProvisionDto
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.mapper.steps.ProvisionsMapper
import com.wini.owle.repo.ProvisionIdFactory
import com.wini.owle.repo.steps.ProvisionRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class ProvisionService(
    repository: ProvisionRepository,
    mapper: ProvisionsMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, ProvisionDto, ProvisionDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)