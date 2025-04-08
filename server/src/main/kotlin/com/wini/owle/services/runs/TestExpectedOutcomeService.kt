package com.wini.owle.services.runs

import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.mapper.runs.TestExpectedOutcomesMapper
import com.wini.owle.repo.TestExpectedOutcomeIdFactory
import com.wini.owle.repo.runs.TestExpectedOutcomeRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TestExpectedOutcomeService(
    repository: TestExpectedOutcomeRepository,
    mapper: TestExpectedOutcomesMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, TestExpectedOutcomeDto, TestExpectedOutcomeDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)