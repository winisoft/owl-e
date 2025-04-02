package com.wini.owle.mapper.runs

import com.wini.owle.dto.*
import com.wini.owle.dto.DtoTestInstanceFactory.benchmarkingDataDto
import com.wini.owle.dto.enums.SubscriptionStatus
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.benchmarkingDataDao
import com.wini.owle.entity.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.SubscriptionStatus.Companion.toDto
import com.wini.owle.entity.runs.BenchmarkingDataDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.mapperModule
import com.wini.owle.mapper.toDecimalValue
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class BenchmarkingDataMapperTest: BaseOwleMapperTest<BenchmarkingDataDto, BenchmarkingDataDao, BenchmarkingDataMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<BenchmarkingDataMapper>(BenchmarkingDataMapper::class.java)
//
//    override val testModule: Module
//        get() = module {
//            includes(mapperModule)
//            single<BenchmarkingDataMapper> { BenchmarkingDataMapper(get()) }
//        }
//
//    override val implicatedTables: List<IdTable<*>> = listOf(BenchmarkingData, WorkflowExecutions, Workflows, Users)
//
//    override fun instantiateMapper(): BenchmarkingDataMapper {
//        return realMapper
//    }
//
//    override fun Transaction.dto(id: Long): BenchmarkingDataDto {
//        return benchmarkingDataDto()
//    }
//
//    override fun Transaction.existingDao(): BenchmarkingDataDao {
//        return benchmarkingDataDao()
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<BenchmarkingDataDao, BenchmarkingDataDto> {
//        val dao = existingDao()
//        val dto = BenchmarkingDataDto(
//            id = dao.id.value,
//            workflowExecution = WorkflowExecutionDto(
//                id = dao.workflowExecution.id.value,
//                workflow = WorkflowDto(
//                    id = dao.workflowExecution.workflow.id.value,
//                    name = dao.workflowExecution.workflow.name,
//                    description = dao.workflowExecution.workflow.description,
//                    pythonVersion = dao.workflowExecution.workflow.pythonVersion,
//                    parentFolder = null,
//                    metadata = UserDesignedMetadataDto.mock,
//                    createdAt = dao.workflowExecution.workflow.createdAt,
//                    updatedAt = dao.workflowExecution.workflow.updatedAt
//                ),
//                user = UserDto(
//                    id = dao.workflowExecution.user.id.value,
//                    email = dao.workflowExecution.user.email,
//                    subscriptionStatus = dao.workflowExecution.user.subscriptionStatus.toDto(),
//                    createdAt = dao.workflowExecution.user.createdAt,
//                    updatedAt = dao.workflowExecution.user.updatedAt,
//                    password = dao.workflowExecution.user.password,
//                    resetToken = dao.workflowExecution.user.resetToken,
//                    oauthProvider = dao.workflowExecution.user.oauthProvider,
//                    oauthId = dao.workflowExecution.user.oauthId,
//                    apiKey = dao.workflowExecution.user.apiKey
//                ),
//                startedAt = dao.workflowExecution.startedAt,
//                completedAt = dao.workflowExecution.completedAt,
//                status = dao.workflowExecution.status.toDto(),
//                stepExecutions = emptyList()
//            ),
//            totalExecutionTime = dao.totalExecutionTime.toDecimalValue(),
//            totalSteps = dao.totalSteps,
//            aiDeterminedSteps = dao.aiDeterminedSteps,
//            predeterminedSteps = dao.predeterminedSteps
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: BenchmarkingDataDao, dto: BenchmarkingDataDto) {
//        assertEquals(dao.workflowExecution.id.value, dto.workflowExecution.id)
//        assertEquals(dao.totalExecutionTime.toDecimalValue(), dto.totalExecutionTime)
//        assertEquals(dao.totalSteps, dto.totalSteps)
//        assertEquals(dao.aiDeterminedSteps, dto.aiDeterminedSteps)
//        assertEquals(dao.predeterminedSteps, dto.predeterminedSteps)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: BenchmarkingDataDto, dao: BenchmarkingDataDao) {
//        assertEquals(dto.workflowExecution.id, dao.workflowExecution.id.value)
//        assertEquals(dto.totalExecutionTime, dao.totalExecutionTime.toDecimalValue())
//        assertEquals(dto.totalSteps, dao.totalSteps)
//        assertEquals(dto.aiDeterminedSteps, dao.aiDeterminedSteps)
//        assertEquals(dto.predeterminedSteps, dao.predeterminedSteps)
//    }
//
//
//}