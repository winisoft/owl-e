package com.wini.owle.services.steps

import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.entity.steps.*
import com.wini.owle.mapper.steps.ProvisionDataPointMapper
import com.wini.owle.repo.ProvisionDataPointIdFactory
import com.wini.owle.repo.steps.*
import com.wini.owle.services.BaseServiceImpl
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointService(
    repository: ProvisionDataPointRepository,
    mapper: ProvisionDataPointMapper,
    entityIdFactory: ProvisionDataPointIdFactory
): BaseServiceImpl<CompositeID, ProvisionDataPoint, ProvisionDataPointEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)
