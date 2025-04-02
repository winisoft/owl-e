package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestInputDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.TestInputs
import com.wini.owle.entity.Users
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.mapper.environs.PackageRepoMapper
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import com.wini.owle.mapper.mapperModule
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class TestInputMapperTest: BaseOwleMapperTest<TestInputDto, TestInputDao, TestInputsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<TestInputsMapper>(TestInputsMapper::class.java)
//
//
//
//    override val implicatedTables: List<IdTable<*>> = listOf(TestInputs, TestCases, Workflows, Users)
//
//    override fun instantiateMapper(): TestInputsMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): TestInputDto {
//        return TestInputDto(
//            id = id,
//            testCaseId = 1L,
//            dataPointName = "Test Data Point",
//            value = "Test Value",
//            generationMethod = GenerationMethodDto.MANUAL
//        )
//    }
//
//    override fun Transaction.existingDao(): TestInputDao {
//        return TestInputDao.new {
//            this.testCase = TestCaseDao.new {
//
//            }
//            this.dataPointName = "Test Data Point"
//            this.value = "Test Value"
//            this.generationMethod = GenerationMethod.MANUAL
//        }
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<TestInputDao, TestInputDto> {
//        val dao = existingDao()
//        val dto = TestInputDto(
//            id = dao.id.value,
//            testCaseId = dao.testCaseId.value,
//            dataPointName = dao.dataPointName,
//            value = dao.value,
//            generationMethod = dao.generationMethod.toDto()
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: TestInputDao, dto: TestInputDto) {
//        assertEquals(dao.testCaseId.value, dto.testCaseId)
//        assertEquals(dao.dataPointName, dto.dataPointName)
//        assertEquals(dao.value, dto.value)
//        assertEquals(dao.generationMethod.toDto(), dto.generationMethod)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: TestInputDto, dao: TestInputDao) {
//        assertEquals(dto.testCaseId, dao.testCaseId.value)
//        assertEquals(dto.dataPointName, dao.dataPointName)
//        assertEquals(dto.value, dao.value)
//        assertEquals(dto.generationMethod, dao.generationMethod.toDto())
//    }
//
//
//}