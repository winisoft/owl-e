package com.wini.owle.mapper.runs

import com.wini.owle.dto.TestExpectedOutcomeDto
import com.wini.owle.dto.enums.ComparisonOperator
import com.wini.owle.entity.ComparisonOperator.Companion.toDto
import com.wini.owle.entity.TestCases
import com.wini.owle.entity.TestExpectedOutcomes
import com.wini.owle.entity.Users
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.runs.TestExpectedOutcomeDao
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
//open class TestExpectedOutcomeMapperTest: BaseOwleMapperTest<TestExpectedOutcomeDto, TestExpectedOutcomeDao, TestExpectedOutcomesMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<TestExpectedOutcomesMapper>(TestExpectedOutcomesMapper::class.java)
//
//
//
//    override val implicatedTables: List<IdTable<*>> = listOf(TestExpectedOutcomes, TestCases, Workflows, Users)
//
//    override fun instantiateMapper(): TestExpectedOutcomesMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): TestExpectedOutcomeDto {
//        return TestExpectedOutcomeDto(
//            id = id,
//            testCaseId = 1L,
//            dataPointName = "Test Data Point",
//            expectedValue = "Expected Value",
//            comparisonOperator = com.wini.owle.entity.ComparisonOperator.EQUALS.toDto()
//        )
//    }
//
//    override fun Transaction.existingDao(): TestExpectedOutcomeDao {
//        return TestExpectedOutcomeDao.new {
//            this.testCaseId = EntityID(1L, TestCases)
//            this.dataPointName = "Test Data Point"
//            this.expectedValue = "Expected Value"
//            this.comparisonOperator = ComparisonOperator.EQUALS
//        }
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<TestExpectedOutcomeDao, TestExpectedOutcomeDto> {
//        val dao = existingDao()
//        val dto = TestExpectedOutcomeDto(
//            id = dao.id.value,
//            testCaseId = dao.testCaseId.value,
//            dataPointName = dao.dataPointName,
//            expectedValue = dao.expectedValue,
//            comparisonOperator = dao.comparisonOperator.toDto()
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: TestExpectedOutcomeDao, dto: TestExpectedOutcomeDto) {
//        assertEquals(dao.testCaseId.value, dto.testCaseId)
//        assertEquals(dao.dataPointName, dto.dataPointName)
//        assertEquals(dao.expectedValue, dto.expectedValue)
//        assertEquals(dao.comparisonOperator.toDto(), dto.comparisonOperator)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: TestExpectedOutcomeDto, dao: TestExpectedOutcomeDao) {
//        assertEquals(dto.testCaseId, dao.testCaseId.value)
//        assertEquals(dto.dataPointName, dao.dataPointName)
//        assertEquals(dto.expectedValue, dao.expectedValue)
//        assertEquals(dto.comparisonOperator, dao.comparisonOperator.toDto())
//    }
//
//
//}