package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestInputDto
import com.wini.owle.entity.GenerationMethod
import com.wini.owle.entity.GenerationMethod.Companion.toDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.TestInputs
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TestInputsMapper(
    private val daoFactory: DaoFactory<TestInputDao, Long>,
    private val testCasesMapper: TestCasesMapper
): DbMapper<TestInputDto, TestInputDao>() {

    override fun toEntity(dto: TestInputDto): TestInputDao {
        val applyProps: TestInputDao.() -> Unit = {
            testCase = testCasesMapper.toEntity(dto.testCase)
            dataPointName = dto.dataPointName
            value = dto.value
            generationMethod = GenerationMethod.fromDto(dto.generationMethod)
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
            // Similar caution as in TestExpectedOutcomesMapper regarding testCase mapping
            testCase = testCasesMapper.toDto(entity.testCase),
            dataPointName = entity.dataPointName,
            value = entity.value,
            generationMethod = entity.generationMethod.toDto()
        )
    }
}