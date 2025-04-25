package com.wini.owle.services.runs

import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.mapper.runs.BenchmarkingDataMapper
import com.wini.owle.repo.BenchmarkingDataIdFactory
import com.wini.owle.repo.runs.BenchmarkingDataRepository
import com.wini.owle.services.BaseServiceImpl


class BenchmarkingDataService(
    repository: BenchmarkingDataRepository,
    mapper: BenchmarkingDataMapper,
    entityIdFactory: BenchmarkingDataIdFactory
): BaseServiceImpl<Long, BenchmarkReport, BenchmarkReportEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)