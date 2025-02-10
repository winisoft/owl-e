package com.wini.owle.mapper.workflows

import com.wini.owle.dto.DtoTestInstanceFactory.workflowDto
import com.wini.owle.dto.WorkflowDto
import com.wini.owle.entity.EntityTestInstanceFactory.workflowDao
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.mapperModule
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class WorkflowsMapperTest: BaseOwleMapperTest<WorkflowDto, WorkflowDao, WorkflowsMapper>() {

    private val realMapper by KoinJavaComponent.inject<WorkflowsMapper>(WorkflowsMapper::class.java)


    override val implicatedTables: List<IdTable<*>> = listOf(Workflows)

    override fun instantiateMapper(): WorkflowsMapper { return realMapper }

    override fun Transaction.dto(id: Long): WorkflowDto {
        return workflowDto(id = id)
    }

    override fun Transaction.existingDao(): WorkflowDao {
        return workflowDao()
    }

    override fun Transaction.existingDaoDto(): Pair<WorkflowDao, WorkflowDto> {
        val dao = existingDao()
        val dto = dao.run {
            workflowDto(
                id = id.value,
                name = name,
                description = description,
                pythonVersion = pythonVersion,
                parentFolder = null
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: WorkflowDao, dto: WorkflowDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
        assertEquals(dao.pythonVersion, dto.pythonVersion)
        assertEquals(dao.metadata, dto.metadata)
        assertEquals(dao.parentFolder?.id?.value, dto.parentFolder?.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: WorkflowDto, dao: WorkflowDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
        assertEquals(dto.pythonVersion, dao.pythonVersion)
        assertEquals(dto.metadata, dao.metadata)
        assertEquals(dto.parentFolder?.id, dao.parentFolder?.id?.value)
    }


}