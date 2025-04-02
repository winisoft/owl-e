package com.wini.owle.mapper.runs

import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.DtoTestInstanceFactory.stepBenchmarkingDataDto
import com.wini.owle.dto.StepBenchmarkingDataDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepBenchmarkingDataDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.entity.runs.StepBenchmarkingDataDao
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.koin.dsl.module
import com.wini.owle.mapper.mapperModule
import com.wini.owle.mapper.toDecimalValue
import org.junit.jupiter.api.Assertions.assertEquals


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class StepBenchmarkingDataMapperTest: BaseOwleMapperTest<StepBenchmarkingDataDto, StepBenchmarkingDataDao, StepBenchmarkingDataMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<StepBenchmarkingDataMapper>(StepBenchmarkingDataMapper::class.java)
//
//
//
//    override val implicatedTables: List<IdTable<*>> = listOf(
//        StepBenchmarkingData,
//        StepExecutions,
//        WorkflowExecutions,
//        Workflows,
//        Users,
//        Steps,
//        Tools,
//        TypeDeclarations,
//        TypeDefinitions,
//        VersionSpecs
//    )
//
//    override fun instantiateMapper(): StepBenchmarkingDataMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): StepBenchmarkingDataDto {
//        return stepBenchmarkingDataDto()
//    }
//
//    override fun Transaction.existingDao(): StepBenchmarkingDataDao {
//        return stepBenchmarkingDataDao(
//            stepExecution = stepExecutionDao()
//        )
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<StepBenchmarkingDataDao, StepBenchmarkingDataDto> {
//        val dao = existingDao()
//        val dto = stepBenchmarkingDataDto(id = dao.id.value)
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: StepBenchmarkingDataDao, dto: StepBenchmarkingDataDto) {
//        assertEquals(dao.stepExecution.id.value, dto.stepExecution.id)
//        assertEquals(dao.executionTime.toDecimalValue(), dto.executionTime)
//        assertEquals(dao.resourceUsage, dto.resourceUsage)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: StepBenchmarkingDataDto, dao: StepBenchmarkingDataDao) {
//        assertEquals(dto.stepExecution.id, dao.stepExecution.id.value)
//        assertEquals(dto.executionTime, dao.executionTime.toDecimalValue())
//        assertEquals(dto.resourceUsage, dao.resourceUsage)
//    }
//
//
//}