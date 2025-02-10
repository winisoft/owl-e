package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestCaseDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.Users
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.environs.PackageRepoMapper
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import com.wini.owle.mapper.mapperModule
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class TestCaseMapperTest: BaseOwleMapperTest<TestCaseDto, TestCaseDao, TestCasesMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<TestCasesMapper>(TestCasesMapper::class.java)
//
//
//
//    override val implicatedTables: List<IdTable<*>> = listOf(TestCases, Workflows, Users)
//
//    override fun instantiateMapper(): TestCasesMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): TestCaseDto {
//        return TestCaseDto(
//            id = id,
//            userId = 1L,
//            name = "Test Case",
//            description = "A test case description",
//            workflowId = 2L,
//            stepId = 3L,
//            inputs = emptyList(),
//            expectedOutcomes = emptyList(),
//            results = emptyList()
//        )
//    }
//
//    override fun Transaction.existingDao(): TestCaseDao {
//        return TestCaseDao.new {
//            this.userId = EntityID(1L, Users)
//            this.workflowId = EntityID(2L, Workflows)
//            this.stepId = EntityID(3L, Steps)
//            this.name = "Test Case"
//            this.description = "A test case description"
//        }
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<TestCaseDao, TestCaseDto> {
//        val dao = existingDao()
//        val dto = TestCaseDto(
//            id = dao.id.value,
//            userId = dao.userId.value,
//            name = dao.name,
//            description = dao.description,
//            workflowId = dao.workflowId?.value,
//            stepId = dao.stepId?.value,
//            inputs = emptyList(), // Inputs require setup
//            expectedOutcomes = emptyList(), // Outcomes require setup
//            results = emptyList() // Results require setup
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: TestCaseDao, dto: TestCaseDto) {
//        assertEquals(dao.userId.value, dto.userId)
//        assertEquals(dao.name, dto.name)
//        assertEquals(dao.description, dto.description)
//        assertEquals(dao.workflowId?.value, dto.workflowId)
//        assertEquals(dao.stepId?.value, dto.stepId)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: TestCaseDto, dao: TestCaseDao) {
//        assertEquals(dto.userId, dao.userId.value)
//        assertEquals(dto.name, dao.name)
//        assertEquals(dto.description, dao.description)
//        assertEquals(dto.workflowId, dao.workflowId?.value)
//        assertEquals(dto.stepId, dao.stepId?.value)
//    }
//
//
//}