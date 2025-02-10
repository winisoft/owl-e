package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestInputDto
import com.wini.owle.entity.GenerationMethod
import com.wini.owle.entity.GenerationMethod.Companion.toDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.TestInputs
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TestInputsMapper(
    private val testCasesMapper: TestCasesMapper
) : DbMapper<TestInputDto, TestInputDao>() {

    override val applyProps: TestInputDao.(TestInputDto) -> Unit = { dto ->
        testCase = testCasesMapper.run { dto.testCase.toEntity() }
        dataPointName = dto.dataPointName
        value = dto.value
        generationMethod = GenerationMethod.fromDto(dto.generationMethod)
    }

    override fun TestInputDto.toEntity(): TestInputDao {
        return if (id == 0L) {
            TestInputDao.new { applyProps(this@toEntity) }
        } else {
            TestInputDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TestInput with id=$id not found")
        }
    }

    override fun TestInputDao.toDto(): TestInputDto {
        return TestInputDto(
            id = id.value,
            testCase = testCasesMapper.run { testCase.toDto() },
            dataPointName = dataPointName,
            value = value,
            generationMethod = generationMethod.toDto()
        )
    }
}