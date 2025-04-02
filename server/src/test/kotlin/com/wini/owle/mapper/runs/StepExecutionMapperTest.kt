package com.wini.owle.mapper.runs

import com.wini.owle.dto.DtoTestInstanceFactory.stepExecutionDto
import com.wini.owle.dto.StepExecutionDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepExecutionDao
import com.wini.owle.entity.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.mapper.environs.PackageRepoMapper
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import com.wini.owle.mapper.mapperModule
import kotlinx.datetime.LocalDateTime
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class StepExecutionMapperTest: BaseOwleMapperTest<StepExecutionDto, StepExecutionDao, StepExecutionsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<StepExecutionsMapper>(StepExecutionsMapper::class.java)
//
//
//
//    override val implicatedTables: List<IdTable<*>> = listOf(
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
//    override fun instantiateMapper(): StepExecutionsMapper { return realMapper }
//
//    override fun Transaction.dto(id: Long): StepExecutionDto { return stepExecutionDto(id = id) }
//
//    override fun Transaction.existingDao(): StepExecutionDao {
//        return stepExecutionDao()
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<StepExecutionDao, StepExecutionDto> {
//        val dao = existingDao()
//        val dto = stepExecutionDto(id = dao.id.value)
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: StepExecutionDao, dto: StepExecutionDto) {
//        assertEquals(dao.workflowExecution, dto.workflowExecution)
//        assertEquals(dao.stepId.value, dto.stepId)
//        assertEquals(dao.startedAt.toJavaLocalDateTime(), java.time.LocalDateTime.parse(dto.startedAt))
//        assertEquals(dao.completedAt.toJavaLocalDateTime(), java.time.LocalDateTime.parse(dto.completedAt))
//        assertEquals(dao.status.toDto(), dto.status)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: StepExecutionDto, dao: StepExecutionDao) {
//        assertEquals(dto.workflowExecutionId, dao.workflowExecutionId.value)
//        assertEquals(dto.stepId, dao.stepId.value)
//        assertEquals(java.time.LocalDateTime.parse(dto.startedAt), dao.startedAt.toJavaLocalDateTime())
//        assertEquals(java.time.LocalDateTime.parse(dto.completedAt), dao.completedAt.toJavaLocalDateTime())
//        assertEquals(dto.status, dao.status.toDto())
//    }
//
//
//}