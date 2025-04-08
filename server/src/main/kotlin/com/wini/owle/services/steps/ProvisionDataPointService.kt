package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.steps.*
import com.wini.owle.mapper.steps.ProvisionDataPointMapper
import com.wini.owle.repo.ProvisionDataPointIdFactory
import com.wini.owle.repo.steps.*
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointService(
    repository: ProvisionDataPointRepository,
    mapper: ProvisionDataPointMapper,
    entityIdFactory: EntityIdFactory<CompositeID>
): BaseServiceImpl<CompositeID, ProvisionDataPointDto, ProvisionDataPointDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)
