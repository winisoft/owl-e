package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointMapper


class TestInputMapper(
    private val daoDelegate: DaoDelegate<ULong, TestInput, TestInputEntity>,
    private val testCasesMapper: TestCasesMapper,
    private val dataPointMapper: DataPointMapper
): DbMapper<ULong, TestInput, TestInputEntity> {

    override fun toEntity(dto: TestInput): TestInputEntity {
        return daoDelegate.findOrCreate(dto) {
            testCase = testCasesMapper.toEntity(dto.testCase)
            dataPoint = dataPointMapper.toEntity(dto.dataPoint)
            value = dto.value
            generationMethod = dto.generationMethod
        }
    }

    override fun toDto(entity: TestInputEntity): TestInput {
        return TestInput(
            id = entity.id.value,
            testCase = testCasesMapper.toDto(entity.testCase),
            dataPoint = dataPointMapper.toDto(entity.dataPoint),
            value = entity.value,
            generationMethod = entity.generationMethod
        )
    }
}