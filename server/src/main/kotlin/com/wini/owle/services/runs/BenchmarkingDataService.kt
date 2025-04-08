package com.wini.owle.services.runs

import com.wini.owle.dto.runs.BenchmarkingDataDto
import com.wini.owle.entity.runs.BenchmarkingDataDao
import com.wini.owle.mapper.runs.BenchmarkingDataMapper
import com.wini.owle.repo.BenchmarkingDataIdFactory
import com.wini.owle.repo.runs.BenchmarkingDataRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class BenchmarkingDataService(
    repository: BenchmarkingDataRepository,
    mapper: BenchmarkingDataMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, BenchmarkingDataDto, BenchmarkingDataDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)