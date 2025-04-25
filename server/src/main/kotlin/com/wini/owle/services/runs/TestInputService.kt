package com.wini.owle.services.runs

import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.mapper.runs.TestInputsMapper
import com.wini.owle.repo.TestInputIdFactory
import com.wini.owle.repo.runs.TestInputRepository
import com.wini.owle.services.BaseServiceImpl


class TestInputService(
    repository: TestInputRepository,
    mapper: TestInputsMapper,
    entityIdFactory: TestInputIdFactory
): BaseServiceImpl<Long, TestInput, TestInputEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)