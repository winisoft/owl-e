package com.wini.owle.mapper.workflows

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.dto.DtoTestInstanceFactory.workflowFolderDto
import com.wini.owle.dto.WorkflowFolderDto
import com.wini.owle.entity.EntityTestInstanceFactory
import com.wini.owle.entity.EntityTestInstanceFactory.workflowFolderDao
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowFolderDao
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
open class WorkflowFolderMapperTest: BaseOwleMapperTest<WorkflowFolderDto, WorkflowFolderDao, WorkflowFoldersMapper>() {

    private val realMapper by KoinJavaComponent.inject<WorkflowFoldersMapper>(WorkflowFoldersMapper::class.java)


    override val implicatedTables: List<IdTable<*>> = ImplicatedTables.WorkflowsModule.WorkflowFolder

    override fun instantiateMapper(): WorkflowFoldersMapper {
        return realMapper
    }

    override fun Transaction.dto(id: Long): WorkflowFolderDto {
        return workflowFolderDto()
    }

    override fun Transaction.existingDao(): WorkflowFolderDao {
        return workflowFolderDao()
    }

    override fun Transaction.existingDaoDto(): Pair<WorkflowFolderDao, WorkflowFolderDto> {
        val dao = existingDao()
        val dto = dao. run {
            workflowFolderDto(
                id = id.value,
                name = name,
                description = description,
                parentFolder = null
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: WorkflowFolderDao, dto: WorkflowFolderDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
        assertEquals(dao.parentFolder?.id?.value, dto.parentFolder?.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: WorkflowFolderDto, dao: WorkflowFolderDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
        assertEquals(dto.parentFolder?.id, dao.parentFolder?.id?.value)
    }


}