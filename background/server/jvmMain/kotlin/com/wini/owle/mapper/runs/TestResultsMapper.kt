package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestResultDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.TestResults
import com.wini.owle.entity.WorkflowExecutions
import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID


class TestResultsMapper(
    private val testCasesMapper: TestCasesMapper,
    private val workflowExecutionsMapper: WorkflowExecutionsMapper
) : DbMapper<TestResultDto, TestResultDao>() {

    override val applyProps: TestResultDao.(TestResultDto) -> Unit = { dto ->
        testCase = testCasesMapper.run { dto.testCase.toEntity() }
        workflowExecution = workflowExecutionsMapper.run { dto.workflowExecution.toEntity() }
        passed = dto.passed
        executedAt = LocalDateTime.parse(dto.executedAt)
    }

    override fun TestResultDto.toEntity(): TestResultDao {
        return if (id == 0L) {
            TestResultDao.new { applyProps(this@toEntity) }
        } else {
            TestResultDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TestResult with id=$id not found")
        }
    }

    override fun TestResultDao.toDto(): TestResultDto {
        return TestResultDto(
            id = id.value,
            testCase = testCasesMapper.run { testCase.toDto() },
            workflowExecution = workflowExecutionsMapper.run { workflowExecution.toDto() },
            passed = passed,
            executedAt = DateTimeConverter.toString(executedAt)
        )
    }
}