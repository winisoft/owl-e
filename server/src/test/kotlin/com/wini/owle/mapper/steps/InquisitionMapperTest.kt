package com.wini.owle.mapper.steps

import com.wini.owle.dto.DtoTestInstanceFactory.dataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.inquisitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.InquisitionDto
import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.EntityTestInstanceFactory.inquisitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.Inquisitions
import com.wini.owle.entity.Steps
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module

import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class InquisitionMapperTest: BaseOwleMapperTest<InquisitionDto, InquisitionDao, InquisitionsMapper>() {

    private val realMapper by KoinJavaComponent.inject<InquisitionsMapper>(InquisitionsMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = listOf(Inquisitions, Steps, DataPoints, Workflows)

    override fun instantiateMapper(): InquisitionsMapper {
        return realMapper
    }

    override fun Transaction.dto(id: Long): InquisitionDto {
        val inquisitionDao = inquisitionDao(
            workflowStep = stepDao(),
            dataPoint = dataPointDao(),
            onMetWorkflowStep = stepDao()
        )
        return inquisitionDto(
            id = inquisitionDao.id.value,
            step = stepDto(
                id = inquisitionDao.workflowStep.id.value
            ),
            dataPoint = dataPointDto(
                id = inquisitionDao.dataPoint.id.value
            ),
            onMetWorkflowStep = stepDto(
                inquisitionDao.onMetWorkflowStep.id.value
            )
        )
    }

    override fun Transaction.existingDao(): InquisitionDao {
        return inquisitionDao()
    }

    override fun Transaction.existingDaoDto(): Pair<InquisitionDao, InquisitionDto> {
        val inquisitionDao = inquisitionDao(
            workflowStep = stepDao(),
            dataPoint = dataPointDao(),
            onMetWorkflowStep = stepDao()
        )
        val inquisitionDto = inquisitionDto(
            id = inquisitionDao.id.value,
            step = stepDto(
                id = inquisitionDao.workflowStep.id.value
            ),
            dataPoint = dataPointDto(
                id = inquisitionDao.dataPoint.id.value
            ),
            onMetWorkflowStep = stepDto(
                inquisitionDao.onMetWorkflowStep.id.value
            )
        )

        return inquisitionDao to inquisitionDto
    }

    override fun assertDaoPropsExpectedMatch(dao: InquisitionDao, dto: InquisitionDto) {
        assertEquals(dao.workflowStep.id.value, dto.step.id)
        assertEquals(dao.className, dto.className)
        assertEquals(dao.dataPoint.id.value, dto.dataPoint.id)
        assertEquals(dao.onMetWorkflowStep.id.value, dto.onMetWorkflowStep.id)
        assertEquals(dao.executionOrder, dto.executionOrder)
    }

    override fun assertDtoPropsExpectedMatch(dto: InquisitionDto, dao: InquisitionDao) {
        assertEquals(dto.step.id, dao.workflowStep.id.value)
        assertEquals(dto.className, dao.className)
        assertEquals(dto.dataPoint.id, dao.dataPoint.id.value)
        assertEquals(dto.onMetWorkflowStep.id, dao.onMetWorkflowStep.id.value)
        assertEquals(dto.executionOrder, dao.executionOrder)
    }

}