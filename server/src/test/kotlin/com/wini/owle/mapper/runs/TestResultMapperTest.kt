package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestResultDto
import com.wini.owle.entity.*
import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class TestResultMapperTest: BaseOwleMapperTest<TestResultDto, TestResultDao, TestResultsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<TestResultsMapper>(TestResultsMapper::class.java)
//
//
//
//    override val implicatedTables: List<IdTable<*>> = listOf(TestResults, TestCases, Workflows, Users, WorkflowExecutions)
//
//    override fun instantiateMapper(): TestResultsMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): TestResultDto {
//        return TestResultDto(
//            id = id,
//            testCaseId = 1L,
//            workflowExecutionId = 2L,
//            passed = true,
//            executedAt = "2025-01-01T12:00:00"
//        )
//    }
//
//    override fun Transaction.existingDao(): TestResultDao {
//        return TestResultDao.new {
//            this.testCase = TestCaseDao.new {
//
//            }
//            this.workflowExecution = WorkflowExecutionDao.new {
//
//            }
//            this.passed = true
//            this.executedAt = java.time.LocalDateTime.parse("2025-01-01T12:00:00").toExposed()
//        }
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<TestResultDao, TestResultDto> {
//        val dao = existingDao()
//        val dto = TestResultDto(
//            id = dao.id.value,
//            testCaseId = dao.testCaseId.value,
//            workflowExecutionId = dao.workflowExecutionId.value,
//            passed = dao.passed,
//            executedAt = DateTimeConverter.toString(dao.executedAt.toJavaLocalDateTime())
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: TestResultDao, dto: TestResultDto) {
//        assertEquals(dao.testCaseId.value, dto.testCaseId)
//        assertEquals(dao.workflowExecutionId.value, dto.workflowExecutionId)
//        assertEquals(dao.passed, dto.passed)
//        assertEquals(dao.executedAt.toJavaLocalDateTime(), java.time.LocalDateTime.parse(dto.executedAt))
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: TestResultDto, dao: TestResultDao) {
//        assertEquals(dto.testCaseId, dao.testCaseId.value)
//        assertEquals(dto.workflowExecutionId, dao.workflowExecutionId.value)
//        assertEquals(dto.passed, dao.passed)
//        assertEquals(java.time.LocalDateTime.parse(dto.executedAt), dao.executedAt.toJavaLocalDateTime())
//    }
//
//
//}