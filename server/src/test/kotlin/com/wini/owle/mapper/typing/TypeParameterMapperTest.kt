package com.wini.owle.mapper.typing

import com.wini.owle.entity.DbLayerIntegrationTests.ImplicatedTables
import com.wini.owle.dto.DtoTestInstanceFactory.typeParameterDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeParameterDao
import com.wini.owle.dto.TypeParameterDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.TypeParameters
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeParameterDao
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
open class TypeParameterMapperTest: BaseOwleMapperTest<TypeParameterDto, TypeParameterDao, TypeParametersMapper>() {

    private val realMapper by KoinJavaComponent.inject<TypeParametersMapper>(TypeParametersMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = ImplicatedTables.TypingModule.TypeParameter

    override fun instantiateMapper(): TypeParametersMapper { return realMapper }

    override fun Transaction.dto(id: Long): TypeParameterDto {
        val typeDefDao = typeDefinitionDao()
        val typeDecDao = typeDeclarationDao()
        val typeParamDao = typeParameterDao()
        return typeParameterDto(id = id)
    }

    override fun Transaction.existingDao(): TypeParameterDao {
        return typeParameterDao()
    }

    override fun Transaction.existingDaoDto(): Pair<TypeParameterDao, TypeParameterDto> {
        val dao = existingDao()
        val dto = typeParameterDto(id = dao.id.value)
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: TypeParameterDao, dto: TypeParameterDto) {
        assertEquals(dao.typeDeclaration.id.value, dto.typeDeclaration.id)
        assertEquals(dao.position, dto.position)
        assertEquals(dao.parameterTypeDeclaration.id.value, dto.parameterTypeDeclaration.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: TypeParameterDto, dao: TypeParameterDao) {
        assertEquals(dto.typeDeclaration.id, dao.typeDeclaration.id.value)
        assertEquals(dto.position, dao.position)
        assertEquals(dto.parameterTypeDeclaration.id, dao.parameterTypeDeclaration.id.value)
    }

}