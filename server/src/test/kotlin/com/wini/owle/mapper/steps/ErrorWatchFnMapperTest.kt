package com.wini.owle.mapper.steps

import com.wini.owle.TestHelper
import com.wini.owle.dto.DtoTestInstanceFactory.errorWatchDto
import com.wini.owle.dto.DtoTestInstanceFactory.errorWatchFnDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.ErrorWatchFnDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchDao
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchFnDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ErrorWatchFnMapperTest: BaseOwleMapperTest<ErrorWatchFnDto, ErrorWatchFnDao, ErrorWatchFnsMapper>() {

    private val realMapper by KoinJavaComponent.inject<ErrorWatchFnsMapper>(ErrorWatchFnsMapper::class.java)

    override val implicatedTables = TestHelper.ImplicatedTables.StepsModule.ErrorWatchFn

    override fun instantiateMapper(): ErrorWatchFnsMapper { return realMapper }

    override fun Transaction.dto(id: Long): ErrorWatchFnDto {
        return errorWatchFnDto(
            id = id,
            errorWatch = errorWatchDto(
                id = errorWatchDao().run {
                    this@run.id.value
                },
                step = stepDao().run {
                    stepDto(id = this@run.id.value)
                },
                triggerStep = stepDao().run {
                    stepDto(id = this@run.id.value)
                }
            )
        )
    }

    override fun Transaction.existingDao(): ErrorWatchFnDao {
        return errorWatchFnDao(
            errorWatch = errorWatchDao(
                step = stepDao(),
                onMetStep = stepDao()
            )
        )
    }

    override fun Transaction.existingDaoDto(): Pair<ErrorWatchFnDao, ErrorWatchFnDto> {
        val dao = existingDao()
        val dto = errorWatchFnDto(
            id = dao.id.value,
            errorWatch = dao.errorWatch.run {
                errorWatchDto(
                    id = id.value,
                    step = step.run {
                        stepDto(
                            id = id.value,
                            name = name,
                            description = description,
                            workflow = workflowDto(id = workflow.id.value)
                        )
                    },
                    errorType = errorType,
                    triggerStep = stepDto(
                        id = onMetStep.id.value,
                        name = onMetStep.name,
                        description = onMetStep.description,
                        workflow = workflowDto(id = onMetStep.id.value)
                    )
                )
            },
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: ErrorWatchFnDao, dto: ErrorWatchFnDto) {
        assertEquals(dao.errorWatch.id.value, dto.errorWatch.id)
        assertEquals(dao.functionName, dto.functionName)
        assertEquals(dao.position, dto.position)
    }

    override fun assertDtoPropsExpectedMatch(dto: ErrorWatchFnDto, dao: ErrorWatchFnDao) {
        assertEquals(dto.errorWatch.id, dao.errorWatch.id.value)
        assertEquals(dto.functionName, dao.functionName)
        assertEquals(dto.position, dao.position)
    }

}