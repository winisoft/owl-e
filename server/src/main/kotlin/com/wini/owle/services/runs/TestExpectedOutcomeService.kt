package com.wini.owle.services.runs

import com.wini.owle.dto.runs.test.TestExpectation
import com.wini.owle.entity.runs.test.TestExpectationEntity
import com.wini.owle.mapper.runs.TestExpectedOutcomesMapper
import com.wini.owle.repo.TestExpectedOutcomeIdFactory
import com.wini.owle.repo.runs.TestExpectedOutcomeRepository
import com.wini.owle.services.BaseServiceImpl


class TestExpectedOutcomeService(
    repository: TestExpectedOutcomeRepository,
    mapper: TestExpectedOutcomesMapper,
    entityIdFactory: TestExpectedOutcomeIdFactory
): BaseServiceImpl<Long, TestExpectation, TestExpectationEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)