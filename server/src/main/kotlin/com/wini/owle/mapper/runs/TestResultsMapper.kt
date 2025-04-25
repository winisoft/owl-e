package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.entity.runs.workflow.WorkflowTestResultEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper


class TestResultsMapper(
    private val daoFactory: DaoFactory<WorkflowTestResultEntity, Long>,
    private val testCasesMapper: TestCasesMapper,
    private val workflowExecutionsMapper: WorkflowExecutionsMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<WorkflowTestResult, WorkflowTestResultEntity>() {

    override fun toEntity(dto: WorkflowTestResult): WorkflowTestResultEntity {
        val applyProps: WorkflowTestResultEntity.() -> Unit = {
            testCaseEntity = testCasesMapper.toEntity(dto.testCase)
            workflowExecution = workflowExecutionsMapper.toEntity(dto.workflowExecution)
            passed = dto.passed
            executedAt = dateTimeConverter.fromString(dto.executedAt)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            WorkflowTestResultEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestResult with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowTestResultEntity): WorkflowTestResult {
        return WorkflowTestResult(
            id = entity.id.value,
            testCase = testCasesMapper.toDto(entity.testCaseEntity),
            workflowExecution = workflowExecutionsMapper.toDto(entity.workflowExecution),
            passed = entity.passed,
            executedAt = dateTimeConverter.toString(entity.executedAt)
        )
    }
}