package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.TestResultDto
import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime // Ensure correct import


class TestResultsMapper(
    private val daoFactory: DaoFactory<TestResultDao, Long>,
    private val testCasesMapper: TestCasesMapper,
    private val workflowExecutionsMapper: WorkflowExecutionsMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<TestResultDto, TestResultDao>() {

    override fun toEntity(dto: TestResultDto): TestResultDao {
        val applyProps: TestResultDao.() -> Unit = {
            testCase = testCasesMapper.toEntity(dto.testCase)
            workflowExecution = workflowExecutionsMapper.toEntity(dto.workflowExecution)
            passed = dto.passed
            executedAt = dateTimeConverter.fromString(dto.executedAt)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TestResultDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestResult with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TestResultDao): TestResultDto {
        return TestResultDto(
            id = entity.id.value,
            testCase = testCasesMapper.toDto(entity.testCase),
            workflowExecution = workflowExecutionsMapper.toDto(entity.workflowExecution),
            passed = entity.passed,
            executedAt = dateTimeConverter.toString(entity.executedAt)
        )
    }
}