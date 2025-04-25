package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.runs.enums.GenerationMethodMapper


class TestInputsMapper(
    private val daoFactory: DaoFactory<TestInputEntity, Long>,
    private val testCasesMapper: TestCasesMapper,
    private val generationMethodMapper: GenerationMethodMapper
): DbMapper<TestInput, TestInputEntity>() {

    override fun toEntity(dto: TestInput): TestInputEntity {
        val applyProps: TestInputEntity.() -> Unit = {
            testCaseEntity = testCasesMapper.toEntity(dto.testCase)
            dataPointName = dto.dataPointName
            value = dto.value
            generationMethod = generationMethodMapper.toEntity(dto.generationMethod)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TestInputEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestInput with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TestInputEntity): TestInput {
        return TestInput(
            id = entity.id.value,
            testCase = testCasesMapper.toDto(entity.testCaseEntity),
            dataPointName = entity.dataPointName,
            value = entity.value,
            generationMethod = generationMethodMapper.toDto(entity.generationMethod)
        )
    }
}