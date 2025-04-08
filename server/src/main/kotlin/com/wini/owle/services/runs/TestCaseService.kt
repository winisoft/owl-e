package com.wini.owle.services.runs

import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.mapper.runs.TestCasesMapper
import com.wini.owle.repo.TestCaseIdFactory
import com.wini.owle.repo.runs.TestCaseRepository
import com.wini.owle.services.BaseServiceImpl
import com.wini.owle.repo.EntityIdFactory


class TestCaseService(
    repository: TestCaseRepository,
    mapper: TestCasesMapper,
    entityIdFactory: EntityIdFactory<Long>
): BaseServiceImpl<Long, TestCaseDto, TestCaseDao>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)