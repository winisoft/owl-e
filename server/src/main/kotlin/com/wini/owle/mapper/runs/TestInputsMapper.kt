package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.TestInputDto
import com.wini.owle.entity.runs.enums.GenerationMethod
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.runs.enums.GenerationMethodMapper


class TestInputsMapper(
    private val daoFactory: DaoFactory<TestInputDao, Long>,
    private val testCasesMapper: TestCasesMapper,
    private val generationMethodMapper: GenerationMethodMapper
): DbMapper<TestInputDto, TestInputDao>() {

    override fun toEntity(dto: TestInputDto): TestInputDao {
        val applyProps: TestInputDao.() -> Unit = {
            testCase = testCasesMapper.toEntity(dto.testCase)
            dataPointName = dto.dataPointName
            value = dto.value
            generationMethod = generationMethodMapper.toEntity(dto.generationMethod)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TestInputDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TestInput with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TestInputDao): TestInputDto {
        return TestInputDto(
            id = entity.id.value,
            testCase = testCasesMapper.toDto(entity.testCase),
            dataPointName = entity.dataPointName,
            value = entity.value,
            generationMethod = generationMethodMapper.toDto(entity.generationMethod)
        )
    }
}