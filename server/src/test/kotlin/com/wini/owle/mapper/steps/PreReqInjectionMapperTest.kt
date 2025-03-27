package com.wini.owle.mapper.steps

import com.wini.owle.dto.DtoTestInstanceFactory.dataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.preReqInjectionDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowJunctionDto
import com.wini.owle.dto.PreReqInjectionDto
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.EntityTestInstanceFactory.inquisitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.preReqInjectionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowJunctionDao
import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class PreReqInjectionMapperTest: BaseOwleMapperTest<PreReqInjectionDto, PreReqInjectionDao, PreReqInjectionsMapper>() {

    private val realMapper by KoinJavaComponent.inject<PreReqInjectionsMapper>(PreReqInjectionsMapper::class.java)

    override fun instantiateMapper(): PreReqInjectionsMapper { return realMapper }

    override fun Transaction.dto(id: Long): PreReqInjectionDto {
        val sourceStep = stepDao()
        val workflowDao = workflowDao()
        val inquisitionDao = inquisitionDao()
        val junctionDao = workflowJunctionDao(
            sourceStep = sourceStep,
            targetWorkflow = workflowDao,
            triggerInquisition = inquisitionDao
        )
        val sourceDataPoint = dataPointDao()
        val injectedDataPoint = dataPointDao()
        val preReqDao = preReqInjectionDao(
            junction = junctionDao,
            sourceDataPoint = sourceDataPoint,
            injectedDataPoint = injectedDataPoint
        )
        return preReqInjectionDto(
            id = id,
            junction = workflowJunctionDto(
                id = junctionDao.id.value
            ),
            sourceDataPoint = dataPointDto(
                id = sourceDataPoint.id.value
            ),
            injectedDataPoint = dataPointDto(
                id = injectedDataPoint.id.value
            )
        )
    }

    override fun Transaction.existingDao(): PreReqInjectionDao {
        return preReqInjectionDao()
    }

    override fun Transaction.existingDaoDto(): Pair<PreReqInjectionDao, PreReqInjectionDto> {
        val dao = existingDao()
        val sourceStep = stepDao()
        val workflowDao = workflowDao()
        val inquisitionDao = inquisitionDao()
        val junctionDao = workflowJunctionDao(
            sourceStep = sourceStep,
            targetWorkflow = workflowDao,
            triggerInquisition = inquisitionDao
        )
        val sourceDataPoint = dataPointDao()
        val injectedDataPoint = dataPointDao()
        val dto = preReqInjectionDto(
            id = dao.id.value,
            junction = workflowJunctionDto(
                id = junctionDao.id.value
            ),
            sourceDataPoint = dataPointDto(
                id = sourceDataPoint.id.value
            ),
            injectedDataPoint = dataPointDto(
                id = injectedDataPoint.id.value
            )
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: PreReqInjectionDao, dto: PreReqInjectionDto) {
        assertEquals(dao.junction.id.value, dto.junction.id)
        assertEquals(dao.sourceDataPoint.id.value, dto.sourceDataPoint.id)
        assertEquals(dao.injectedDataPoint.id.value, dto.injectedDataPoint.id)
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
    }

    override fun assertDtoPropsExpectedMatch(dto: PreReqInjectionDto, dao: PreReqInjectionDao) {
        assertEquals(dto.junction.id, dao.junction.id.value)
        assertEquals(dto.sourceDataPoint.id, dao.sourceDataPoint.id.value)
        assertEquals(dto.injectedDataPoint.id, dao.injectedDataPoint.id.value)
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
    }

}
