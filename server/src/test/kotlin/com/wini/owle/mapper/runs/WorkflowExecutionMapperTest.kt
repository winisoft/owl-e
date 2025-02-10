package com.wini.owle.mapper.runs

import com.wini.owle.dto.WorkflowExecutionDto
import com.wini.owle.entity.Users
import com.wini.owle.entity.WorkflowExecutions
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class WorkflowExecutionMapperTest: BaseOwleMapperTest<WorkflowExecutionDto, WorkflowExecutionDao, WorkflowExecutionsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<WorkflowExecutionsMapper>(WorkflowExecutionsMapper::class.java)
//
//
//
//    override val implicatedTables: List<IdTable<*>> = listOf(WorkflowExecutions, Workflows, Users)
//
//    override fun instantiateMapper(): WorkflowExecutionsMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): WorkflowExecutionDto {
//        return WorkflowExecutionDto(
//            id = id,
//            workflowId = 1L,
//            userId = 2L,
//            startedAt = "2025-01-01T10:00:00",
//            completedAt = "2025-01-01T12:00:00",
//            status = ExecutionStatusDto.COMPLETED,
//            stepExecutions = emptyList()
//        )
//    }
//
//    override fun Transaction.existingDao(): WorkflowExecutionDao {
//        return WorkflowExecutionDao.new {
//            this.workflowId = EntityID(1L, Workflows)
//            this.userId = EntityID(2L, Users)
//            this.startedAt = java.time.LocalDateTime.parse("2025-01-01T10:00:00").toExposed()
//            this.completedAt = java.time.LocalDateTime.parse("2025-01-01T12:00:00").toExposed()
//            this.status = ExecutionStatus.COMPLETED
//        }
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<WorkflowExecutionDao, WorkflowExecutionDto> {
//        val dao = existingDao()
//        val dto = WorkflowExecutionDto(
//            id = dao.id.value,
//            workflowId = dao.workflowId.value,
//            userId = dao.userId.value,
//            startedAt = DateTimeConverter.toString(dao.startedAt.toJavaLocalDateTime()),
//            completedAt = DateTimeConverter.toString(dao.completedAt.toJavaLocalDateTime()),
//            status = dao.status.toDto(),
//            stepExecutions = emptyList() // Step executions require a setup
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: WorkflowExecutionDao, dto: WorkflowExecutionDto) {
//        assertEquals(dao.workflowId.value, dto.workflowId)
//        assertEquals(dao.userId.value, dto.userId)
//        assertEquals(dao.startedAt.toJavaLocalDateTime(), java.time.LocalDateTime.parse(dto.startedAt))
//        assertEquals(dao.completedAt.toJavaLocalDateTime(), java.time.LocalDateTime.parse(dto.completedAt))
//        assertEquals(dao.status.toDto(), dto.status)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: WorkflowExecutionDto, dao: WorkflowExecutionDao) {
//        assertEquals(dto.userId, dao.userId.value)
//        assertEquals(java.time.LocalDateTime.parse(dto.startedAt), dao.startedAt.toJavaLocalDateTime())
//        assertEquals(java.time.LocalDateTime.parse(dto.completedAt), dao.completedAt.toJavaLocalDateTime())
//        assertEquals(dto.status, dao.status.toDto())
//    }
//
//
//}