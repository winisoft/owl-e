package com.wini.owle.services.runs

import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.mapper.runs.TestCasesMapper
import com.wini.owle.repo.TestCaseIdFactory
import com.wini.owle.repo.runs.TestCaseRepository
import com.wini.owle.services.BaseServiceImpl


class TestCaseService(
    repository: TestCaseRepository,
    mapper: TestCasesMapper,
    entityIdFactory: TestCaseIdFactory
): BaseServiceImpl<Long, TestCase, TestCaseEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)