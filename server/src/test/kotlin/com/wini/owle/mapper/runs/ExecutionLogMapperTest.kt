package com.wini.owle.mapper.runs

import com.wini.owle.dto.DtoTestInstanceFactory.executionLogDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepExecutionDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowExecutionDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowFolderDto
import com.wini.owle.dto.ExecutionLogDto
import com.wini.owle.dto.StepExecutionDto
import com.wini.owle.dto.UserDesignedMetadataDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.executionLogDao
import com.wini.owle.entity.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.LogLevel.Companion.toDto
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.environs.PackageRepoMapper
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.koin.core.module.*
import org.koin.dsl.module
import com.wini.owle.mapper.mapperModule
import org.junit.jupiter.api.Assertions.assertEquals


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class ExecutionLogMapperTest: BaseOwleMapperTest<ExecutionLogDto, ExecutionLogDao, ExecutionLogMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<ExecutionLogMapper>(ExecutionLogMapper::class.java)
//
//    override val testModule: Module
//        get() = module {
//            includes(mapperModule)
//            single<ExecutionLogMapper> { ExecutionLogMapper(get()) }
//        }
//    override val implicatedTables: List<IdTable<*>> = listOf(
//        ExecutionLogs,
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
//    override fun instantiateMapper(): ExecutionLogMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): ExecutionLogDto {
//        return executionLogDto()
//    }
//
//    override fun Transaction.existingDao(): ExecutionLogDao {
//        return executionLogDao()
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<ExecutionLogDao, ExecutionLogDto> {
//        val dao = existingDao()
//        val dto = dao.run {
//            ExecutionLogDto(
//                id = id.value,
//                timestamp = timestamp,
//                logLevel = logLevel.toDto(),
//                message = message,
//                stepExecution = stepExecution.run {
//                    stepExecutionDto(
//                        id = id.value,
//                        workflowExecution = workflowExecution.run {
//                            workflowExecutionDto(
//                                id = id.value,
//                                workflow = workflow.run {
//                                    workflowDto(
//                                        id = id.value,
//                                        name = name,
//                                        description = description,
//                                        pythonVersion = pythonVersion,
//                                        parentFolder = null,
//                                        metadata = UserDesignedMetadataDto.mock,
//                                        createdAt = createdAt,
//                                        updatedAt = updatedAt
//                                    )
//                                }
//                            )
//                        },
//                        step = dao.stepExecution.step.run {
//                            stepDto(
//                                id = id.value,
//                                name = name,
//                                description = description,
//                                workflow = workflow.run {
//                                    workflowDto(
//                                        id = id.value,
//                                        name = name,
//                                        description = description,
//                                        pythonVersion = pythonVersion,
//                                        parentFolder = null,
//                                        metadata = UserDesignedMetadataDto.mock,
//                                        createdAt = createdAt,
//                                        updatedAt = updatedAt
//                                    )
//                                }
//                            )
//                        },
//                        startedAt = dao.stepExecution.startedAt,
//                        completedAt = dao.stepExecution.completedAt,
//                        status = dao.stepExecution.status.toDto()
//                    )
//                }
//            )
//        }
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: ExecutionLogDao, dto: ExecutionLogDto) {
//        assertEquals(dao.stepExecution.id.value, dto.stepExecution.id)
//        assertEquals(dao.timestamp.toJavaLocalDateTime(), java.time.LocalDateTime.parse(dto.timestamp))
//        assertEquals(dao.logLevel.toDto(), dto.logLevel)
//        assertEquals(dao.message, dto.message)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: ExecutionLogDto, dao: ExecutionLogDao) {
//        assertEquals(dto.stepExecution.id, dao.stepExecution.id.value)
//        assertEquals(java.time.LocalDateTime.parse(dto.timestamp), dao.timestamp.toJavaLocalDateTime())
//        assertEquals(dto.logLevel, dao.logLevel.toDto())
//        assertEquals(dto.message, dao.message)
//    }
//
//}