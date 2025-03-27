package com.wini.owle.mapper.steps

import com.wini.owle.dto.DtoTestInstanceFactory.inquisitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowJunctionDto
import com.wini.owle.dto.WorkflowJunctionDto
import com.wini.owle.entity.EntityTestInstanceFactory.inquisitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.entity.EntityTestInstanceFactory.workflowJunctionDao
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class WorkflowJunctionMapperTest: BaseOwleMapperTest<WorkflowJunctionDto, WorkflowJunctionDao, WorkflowJunctionsMapper>() {

    private val realMapper by KoinJavaComponent.inject<WorkflowJunctionsMapper>(WorkflowJunctionsMapper::class.java)


    override fun instantiateMapper(): WorkflowJunctionsMapper { return realMapper }

    override fun Transaction.dto(id: Long): WorkflowJunctionDto {
        val sourceStepDao = stepDao()
        val workflowDao = workflowDao()
        val inquisitionDao = inquisitionDao()

        return workflowJunctionDto(
            id = id,
            sourceStep = stepDto(
                id = sourceStepDao.id.value
            ),
            targetWorkflow = workflowDto(
                id = workflowDao.id.value
            ),
            triggerInquisition = inquisitionDto(
                id = inquisitionDao.id.value
            )
        )
    }

    override fun Transaction.existingDao(): WorkflowJunctionDao {
        return workflowJunctionDao()
    }

    override fun Transaction.existingDaoDto(): Pair<WorkflowJunctionDao, WorkflowJunctionDto> {
        val dao = existingDao()
        val sourceStepDao = stepDao()
        val workflowDao = workflowDao()
        val inquisitionDao = inquisitionDao()
        val dto = workflowJunctionDto(
            id = dao.id.value,
            sourceStep = stepDto(
                id = sourceStepDao.id.value
            ),
            targetWorkflow = workflowDto(
                id = workflowDao.id.value
            ),
            triggerInquisition = inquisitionDto(
                id = inquisitionDao.id.value
            )
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: WorkflowJunctionDao, dto: WorkflowJunctionDto) {
        assertEquals(dao.sourceStep.id.value, dto.sourceStep.id)
        assertEquals(dao.targetWorkflow.id.value, dto.targetWorkflow.id)
        assertEquals(dao.triggerInquisition?.id?.value, dto.triggerInquisition?.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: WorkflowJunctionDto, dao: WorkflowJunctionDao) {
        assertEquals(dto.sourceStep.id, dao.sourceStep.id.value)
        assertEquals(dto.targetWorkflow.id, dao.targetWorkflow.id.value)
        assertEquals(dto.triggerInquisition?.id, dao.triggerInquisition?.id?.value)
    }

}