package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.TestExpectation
import com.wini.owle.entity.runs.test.TestExpectationEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.runs.enums.ComparisonOperatorMapper


class TestExpectedOutcomesMapper(
    private val daoFactory: DaoFactory<TestExpectationEntity, Long>,
    private val testCasesMapper: TestCasesMapper,
    private val comparisonOperatorMapper: ComparisonOperatorMapper
): DbMapper<TestExpectation, TestExpectationEntity>() {

    override fun toEntity(dto: TestExpectation): TestExpectationEntity {
        val applyProps: TestExpectationEntity.() -> Unit = {
            testCaseEntity = testCasesMapper.toEntity(dto.testCase)
            dataPointName = dto.dataPointName
            expectedValue = dto.expectedValue
            comparisonOperator = comparisonOperatorMapper.toEntity(dto.comparisonOperator)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TestExpectationEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestExpectedOutcome with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TestExpectationEntity): TestExpectation {
        return TestExpectation(
            id = entity.id.value,
            testCase = testCasesMapper.toDto(entity.testCaseEntity),
            dataPointName = entity.dataPointName,
            expectedValue = entity.expectedValue,
            comparisonOperator = comparisonOperatorMapper.toDto(entity.comparisonOperator)
        )
    }
}