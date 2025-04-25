package com.wini.owle.services.runs

import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.entity.runs.workflow.WorkflowTestResultEntity
import com.wini.owle.mapper.runs.TestResultsMapper
import com.wini.owle.repo.TestResultIdFactory
import com.wini.owle.repo.runs.TestResultRepository
import com.wini.owle.services.BaseServiceImpl


class TestResultService(
    repository: TestResultRepository,
    mapper: TestResultsMapper,
    entityIdFactory: TestResultIdFactory
): BaseServiceImpl<Long, WorkflowTestResult, WorkflowTestResultEntity>(
    repository = repository,
    mapper = mapper,
    entityIdFactory = entityIdFactory
)