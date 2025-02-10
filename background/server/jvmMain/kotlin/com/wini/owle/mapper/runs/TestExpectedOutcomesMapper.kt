package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestExpectedOutcomeDto
import com.wini.owle.entity.ComparisonOperator
import com.wini.owle.entity.ComparisonOperator.Companion.toDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.TestExpectedOutcomes
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TestExpectedOutcomesMapper(
    private val testCasesMapper: TestCasesMapper
) : DbMapper<TestExpectedOutcomeDto, TestExpectedOutcomeDao>() {

    override val applyProps: TestExpectedOutcomeDao.(TestExpectedOutcomeDto) -> Unit = { dto ->
        testCase = testCasesMapper.run { dto.testCase.toEntity() }
        dataPointName = dto.dataPointName
        expectedValue = dto.expectedValue
        comparisonOperator = ComparisonOperator.fromDto(dto.comparisonOperator)
    }

    override fun TestExpectedOutcomeDto.toEntity(): TestExpectedOutcomeDao {
        return if (id == 0L) {
            TestExpectedOutcomeDao.new { applyProps(this@toEntity) }
        } else {
            TestExpectedOutcomeDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TestExpectedOutcome with id=$id not found")
        }
    }

    override fun TestExpectedOutcomeDao.toDto(): TestExpectedOutcomeDto {
        return TestExpectedOutcomeDto(
            id = id.value,
            testCase = testCasesMapper.run { testCase.toDto() },
            dataPointName = dataPointName,
            expectedValue = expectedValue,
            comparisonOperator = comparisonOperator.toDto()
        )
    }
}