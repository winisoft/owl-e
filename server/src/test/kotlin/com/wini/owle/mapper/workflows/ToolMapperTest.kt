package com.wini.owle.mapper.workflows

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.dto.DtoTestInstanceFactory.toolDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.ToolDto
import com.wini.owle.entity.EntityTestInstanceFactory.toolDao
import com.wini.owle.entity.Tools
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ToolMapperTest: BaseOwleMapperTest<ToolDto, ToolDao, ToolsMapper>() {

    private val realMapper by KoinJavaComponent.inject<ToolsMapper>(ToolsMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = ImplicatedTables.WorkflowsModule.Tool

    override fun instantiateMapper(): ToolsMapper { return realMapper }

    override fun Transaction.dto(id: Long): ToolDto { return toolDto() }

    override fun Transaction.existingDao(): ToolDao { return toolDao() }

    override fun Transaction.existingDaoDto(): Pair<ToolDao, ToolDto> {
        val dao = existingDao()
        val dto = dao.run {
            toolDto(
                id = dao.id.value,
                name = dao.name,
                description = dao.description,
                body = dao.body,
                returnTypeDeclaration = returnTypeDeclaration.run {
                    typeDeclarationDto(id = id.value)
                }
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: ToolDao, dto: ToolDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
        assertEquals(dao.body, dto.body)
        assertEquals(dao.returnTypeDeclaration?.id?.value, dto.returnTypeDeclaration?.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: ToolDto, dao: ToolDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
        assertEquals(dto.body, dao.body)
        assertEquals(dto.returnTypeDeclaration?.id, dao.returnTypeDeclaration?.id?.value)
    }

}
