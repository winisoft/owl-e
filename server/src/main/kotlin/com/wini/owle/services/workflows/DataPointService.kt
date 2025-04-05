package com.wini.owle.services.workflows

import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.repo.DataPointIdFactory
import com.wini.owle.repo.workflows.DataPointRepository
import com.wini.owle.services.BaseServiceImpl


class DataPointService(
    repository: DataPointRepository,
    mapper: DataPointsMapper,
    entityIdFactory: DataPointIdFactory
): BaseServiceImpl<Long, DataPointDto, DataPointDao>(
    repository,
    mapper,
    entityIdFactory
)