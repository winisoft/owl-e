package com.wini.owle.services.runs

import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.mapper.runs.TestExpectedOutcomesMapper
import com.wini.owle.repo.runs.TestExpectedOutcomeRepository
import com.wini.owle.services.BaseServiceImpl


class TestExpectedOutcomeService(
    repository: TestExpectedOutcomeRepository,
    mapper: TestExpectedOutcomesMapper
): BaseServiceImpl<Long, TestExpectedOutcomeDto, TestExpectedOutcomeDao>(repository, mapper)