package com.wini.owle.mapper.workflows

import com.wini.owle.dto.DtoTestInstanceFactory.phaseDto
import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.PhaseDto
import com.wini.owle.dto.UserDesignedMetadataDto
import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.EntityTestInstanceFactory.phaseDao
import com.wini.owle.entity.Steps
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.environs.PackageRepoMapper
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class PhaseMapperTest: BaseOwleMapperTest<PhaseDto, PhaseDao, PhasesMapper>() {

    private val realMapper by KoinJavaComponent.inject<PhasesMapper>(PhasesMapper::class.java)

        override val implicatedTables: List<IdTable<*>> = listOf(WorkflowFolders, Workflows, Steps, DataPoints)

    override fun instantiateMapper(): PhasesMapper {
        return realMapper
    }

    override fun Transaction.dto(id: Long): PhaseDto {
        return phaseDto()
    }

    override fun Transaction.existingDao(): PhaseDao {
        return phaseDao()
    }

    override fun Transaction.existingDaoDto(): Pair<PhaseDao, PhaseDto> {
        val dao = existingDao()
        val dto = dao.run {
            phaseDto(
                id = id.value,
                workflow = workflow.run {
                    workflowDto(
                        id = id.value,
                        name = name,
                        description = description,
                        pythonVersion = pythonVersion,
                        parentFolder = null,
                        metadata = UserDesignedMetadataDto.mock,
                        createdAt = DateTimeConverter.toString(createdAt),
                        updatedAt = DateTimeConverter.toString(updatedAt)
                    )
                },
                parentPhase = null,
                name = name,
                description = description,
                displayOrder = displayOrder
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: PhaseDao, dto: PhaseDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
        assertEquals(dao.workflow.id.value, dto.workflow.id)
        assertEquals(dao.parentPhase, dto.parentPhase)
    }

    override fun assertDtoPropsExpectedMatch(dto: PhaseDto, dao: PhaseDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
        assertEquals(dto.workflow.id, dao.workflow.id.value)
        assertEquals(dto.parentPhase, dao.parentPhase)
    }


}