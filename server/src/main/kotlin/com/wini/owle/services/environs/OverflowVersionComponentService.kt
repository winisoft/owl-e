package com.wini.owle.services.environs

import com.wini.owle.dto.environs.VersionOverflow
import com.wini.owle.entity.environs.OverflowVersionComponentEntity
import com.wini.owle.mapper.environs.OverflowVersionComponentMapper
import com.wini.owle.repo.OverflowVersionComponentEntityIdFactory
import com.wini.owle.repo.environs.OverflowVersionComponentRepository
import com.wini.owle.services.BaseServiceImpl


class OverflowVersionComponentService(
    repository: OverflowVersionComponentRepository,
    mapper: OverflowVersionComponentMapper,
    entityIdFactory: OverflowVersionComponentEntityIdFactory
): BaseServiceImpl<Long, VersionOverflow, OverflowVersionComponentEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)