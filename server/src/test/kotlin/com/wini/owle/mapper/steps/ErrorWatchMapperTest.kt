package com.wini.owle.mapper.steps

import com.wini.owle.dto.DtoTestInstanceFactory.errorWatchDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.toolDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.entity.EntityTestInstanceFactory.errorWatchDao
import com.wini.owle.dto.ErrorWatchDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ErrorWatchMapperTest: BaseOwleMapperTest<ErrorWatchDto, ErrorWatchDao, ErrorWatchesMapper>() {

    private val realMapper by KoinJavaComponent.inject<ErrorWatchesMapper>(ErrorWatchesMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = listOf(
        ErrorWatchFns,
        ErrorWatches,
        Steps,
        Workflows,
        Tools,
        TypeDeclarations,
        TypeDefinitions,
        VersionSpecs
    )

    override fun instantiateMapper(): ErrorWatchesMapper { return realMapper }

    override fun Transaction.dto(id: Long): ErrorWatchDto {
        val executingStepDao = stepDao()
        val defaultNextStepDao = stepDao()
        val executingStepDto = stepDto(
            id = executingStepDao.id.value,
            name = executingStepDao.name,
            description = executingStepDao.description,
            workflow = workflowDao().run {
                workflowDto(id = id, name = name, description = description, pythonVersion = pythonVersion)
            },
            tool = toolDao().run { toolDto(this.id.value) },
            nextStep = stepDto(id = defaultNextStepDao.id.value),
        )
        val onMetStepDao = stepDao()
        val onMetStepDto = stepDto(
            id = onMetStepDao.id.value,
            name = onMetStepDao.name,
            description = onMetStepDao.description,
            workflow = workflowDao().run {
                workflowDto(id = id, name = name, description = description, pythonVersion = pythonVersion)
            },
            tool = toolDao().run { toolDto(this.id.value) },
            nextStep = stepDao().run { stepDto(id = this.id.value) },
        )
        return errorWatchDto(
            id = id,
            step = executingStepDto,
            triggerStep = onMetStepDto
        )
    }

    override fun Transaction.existingDao(): ErrorWatchDao {
        return errorWatchDao()
    }

    override fun Transaction.existingDaoDto(): Pair<ErrorWatchDao, ErrorWatchDto> {
        val executingStepDao = stepDao()
        val defaultNextStepDao = stepDao()
        val executingStepDto = stepDto(
            id = executingStepDao.id.value,
            name = executingStepDao.name,
            description = executingStepDao.description,
            workflow = workflowDao().run {
                workflowDto(
                    id = this.id.value,
                    name = name,
                    description = description,
                    pythonVersion = pythonVersion
                )
            },
            tool = toolDao().run { toolDto(this.id.value) },
            nextStep = stepDto(id = defaultNextStepDao.id.value),
        )
        val onMetStepDao = stepDao()
        val onMetStepDto = stepDto(
            id = onMetStepDao.id.value,
            name = onMetStepDao.name,
            description = onMetStepDao.description,
            workflow = workflowDao().run {
                workflowDto(
                    id = this.id.value,
                    name = name,
                    description = description,
                    pythonVersion = pythonVersion
                )
            },
            tool = toolDao().run { toolDto(this.id.value) },
            nextStep = stepDao().run { stepDto(id = this.id.value) },
        )
        val errorWatchDao = errorWatchDao(
            step = executingStepDao,
            onMetStep = onMetStepDao
        )
        val errorWatchDto = errorWatchDto(
            id = errorWatchDao.id.value,
            step = executingStepDto,
            triggerStep = onMetStepDto
        )
        return errorWatchDao to errorWatchDto
    }

    override fun assertDaoPropsExpectedMatch(dao: ErrorWatchDao, dto: ErrorWatchDto) {
        assertEquals(dao.step.id.value, dto.step.id)
        assertEquals(dao.errorType, dto.errorType)
        assertEquals(dao.onMetStep.id.value, dto.triggerStep.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: ErrorWatchDto, dao: ErrorWatchDao) {
        assertEquals(dto.step.id, dao.step.id.value)
        assertEquals(dto.errorType, dao.errorType)
        assertEquals(dto.triggerStep.id, dao.onMetStep.id.value)
    }

}