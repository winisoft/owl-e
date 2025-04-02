package com.wini.owle.services.runs

import com.wini.owle.dto.runs.TestResultDto
import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.mapper.runs.TestResultsMapper
import com.wini.owle.repo.runs.TestResultRepository
import com.wini.owle.services.BaseServiceImpl


class TestResultService(
    repository: TestResultRepository,
    mapper: TestResultsMapper
): BaseServiceImpl<Long, TestResultDto, TestResultDao>(repository, mapper)