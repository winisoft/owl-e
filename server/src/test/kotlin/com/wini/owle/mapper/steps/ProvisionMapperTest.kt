package com.wini.owle.mapper.steps

import com.wini.owle.TestHelper
import com.wini.owle.dto.DtoTestInstanceFactory.dataPointDto
import com.wini.owle.dto.DtoTestInstanceFactory.provisionDto
import com.wini.owle.dto.DtoTestInstanceFactory.stepDto
import com.wini.owle.dto.DtoTestInstanceFactory.toolParamDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.ProvisionDto
import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.dataPointDao
import com.wini.owle.entity.EntityTestInstanceFactory.provisionDao
import com.wini.owle.entity.EntityTestInstanceFactory.stepDao
import com.wini.owle.entity.EntityTestInstanceFactory.toolParamDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.StoreMethod.Companion.toDto
import com.wini.owle.entity.steps.ProvisionDao
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
open class ProvisionMapperTest: BaseOwleMapperTest<ProvisionDto, ProvisionDao, ProvisionsMapper>() {

    private val realMapper by KoinJavaComponent.inject<ProvisionsMapper>(ProvisionsMapper::class.java)
    override val implicatedTables: List<IdTable<*>> = TestHelper.ImplicatedTables.StepsModule.Provision

    override fun instantiateMapper(): ProvisionsMapper { return realMapper }

    override fun Transaction.dto(id: Long): ProvisionDto {
        val stepDao = stepDao()
        val stepDto = stepDto(id = stepDao.id.value)
        val toolParamDao = toolParamDao()
        val toolParamDto = toolParamDto(id = toolParamDao.id.value)
        return provisionDto(
            id = id,
            step = stepDto,
            toolParam = toolParamDto
        )
    }

    override fun Transaction.existingDao(): ProvisionDao {
        return provisionDao()
    }

    override fun Transaction.existingDaoDto(): Pair<ProvisionDao, ProvisionDto> {
        val dao = existingDao()
        val stepDao = stepDao()
        val stepDto = stepDto(id = stepDao.id.value)
        val toolParamDao = toolParamDao()
        val toolParamDto = toolParamDto(id = toolParamDao.id.value)
        val dto = provisionDto(
            id = dao.id.value,
            step = stepDto,
            toolParam = toolParamDto
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: ProvisionDao, dto: ProvisionDto) {
        assertEquals(dao.step.id.value, dto.step.id)
        assertEquals(dao.toolParam.id.value, dto.toolParam.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: ProvisionDto, dao: ProvisionDao) {
        assertEquals(dto.step.id, dao.step.id.value)
        assertEquals(dto.toolParam.id, dao.toolParam.id.value)
    }

}