package com.wini.owle.mapper.workflows

import com.wini.owle.dto.DtoTestInstanceFactory.toolDto
import com.wini.owle.dto.DtoTestInstanceFactory.toolParamDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.ToolParamDto
import com.wini.owle.entity.EntityTestInstanceFactory.toolParamDao
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ToolParamMapperTest: BaseOwleMapperTest<ToolParamDto, ToolParamDao, ToolParamsMapper>() {

    private val realMapper by KoinJavaComponent.inject<ToolParamsMapper>(ToolParamsMapper::class.java)


    override fun instantiateMapper(): ToolParamsMapper { return realMapper }

    override fun Transaction.dto(id: Long): ToolParamDto { return toolParamDto(id = id) }

    override fun Transaction.existingDao(): ToolParamDao { return toolParamDao() }

    override fun Transaction.existingDaoDto(): Pair<ToolParamDao, ToolParamDto> {
        val dao = existingDao()
        val dto = dao.run {
            toolParamDto(
                id = id.value,
                name = name,
                typeDeclaration = typeDeclarationDto(),
                hasDefault = dao.hasDefault,
                tool = toolDto()
            )
        }
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: ToolParamDao, dto: ToolParamDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.typeDeclaration.id.value, dto.typeDeclaration.id)
        assertEquals(dao.hasDefault, dto.hasDefault)
        assertEquals(dao.tool.id.value, dto.tool.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: ToolParamDto, dao: ToolParamDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.typeDeclaration.id, dao.typeDeclaration.id.value)
        assertEquals(dto.hasDefault, dao.hasDefault)
        assertEquals(dto.tool.id, dao.tool.id.value)
    }

}