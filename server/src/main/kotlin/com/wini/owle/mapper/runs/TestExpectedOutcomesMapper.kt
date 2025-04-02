package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestExpectedOutcomeDto
import com.wini.owle.entity.ComparisonOperator
import com.wini.owle.entity.ComparisonOperator.Companion.toDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.TestExpectedOutcomes
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TestExpectedOutcomesMapper(
    private val daoFactory: DaoFactory<TestExpectedOutcomeDao, Long>,
    private val testCasesMapper: TestCasesMapper
): DbMapper<TestExpectedOutcomeDto, TestExpectedOutcomeDao>() {

    override fun toEntity(dto: TestExpectedOutcomeDto): TestExpectedOutcomeDao {
        val applyProps: TestExpectedOutcomeDao.() -> Unit = {
            testCase = testCasesMapper.toEntity(dto.testCase)
            dataPointName = dto.dataPointName
            expectedValue = dto.expectedValue
            comparisonOperator = ComparisonOperator.fromDto(dto.comparisonOperator)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TestExpectedOutcomeDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestExpectedOutcome with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TestExpectedOutcomeDao): TestExpectedOutcomeDto {
        return TestExpectedOutcomeDto(
            id = entity.id.value,
            testCase = testCasesMapper.toDto(entity.testCase),
            dataPointName = entity.dataPointName,
            expectedValue = entity.expectedValue,
            comparisonOperator = entity.comparisonOperator.toDto()
        )
    }
}