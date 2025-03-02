package com.wini.owle.mapper.typing

import com.wini.owle.TestHelper
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.TypeVarianceDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.TypeVariances
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.dto.DtoTestInstanceFactory.typeVarianceDto
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeVarianceDao
import com.wini.owle.entity.EntityTestInstanceFactory.versionSpecDao
import com.wini.owle.entity.VarianceCardinality.Companion.toDto
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance

import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class TypeVariancesMapperTest: BaseOwleMapperTest<TypeVarianceDto, TypeVarianceDao, TypeVariancesMapper>() {

    private val realMapper by KoinJavaComponent.inject<TypeVariancesMapper>(TypeVariancesMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = TestHelper.ImplicatedTables.TypingModule.TypeVariance

    override fun instantiateMapper(): TypeVariancesMapper { return realMapper }

    override fun Transaction.dto(id: Long): TypeVarianceDto {
        val typeVariableDeclarationDao = typeDeclarationDao()
        return typeVarianceDto(
            id = id,
            typeVariableDeclaration = typeDeclarationDto(
                id = typeVariableDeclarationDao.id.value
            )
        )
    }

    override fun Transaction.existingDao(): TypeVarianceDao {
        return typeVarianceDao()
    }

    override fun Transaction.existingDaoDto(): Pair<TypeVarianceDao, TypeVarianceDto> {
        val dao = existingDao()
        val versionSpecDao = versionSpecDao()
        val versionSpecDto = versionSpecDto(
            id = versionSpecDao.id.value
        )
        val typeVarDefDao = typeDefinitionDao(versionSpec = versionSpecDao)
        val typeVarianceDto = typeVarianceDto(id = typeVarDefDao.id.value)
        val typeVarDecDao = typeDeclarationDao(typeDefinition = typeVarDefDao)
        val typeVarDecDto = typeDeclarationDto(id = typeVarDecDao.id.value, typeDefinition = typeDefinitionDto(id = typeVarDefDao.id.value, versionSpec = versionSpecDto))
        val typeVarDefDto = typeDefinitionDto(
            id = typeVarDefDao.id.value,
            versionSpec = versionSpecDto
        )

        val dto = typeVarianceDto(
            id = dao.id.value,
            typeVariableDeclaration = typeVarDecDto
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: TypeVarianceDao, dto: TypeVarianceDto) {
        assertEquals(dao.typeVariableDeclaration.id.value, dto.typeVariableDeclaration.id)
        assertEquals(dao.varianceCardinality.toDto(), dto.varianceCardinality)
    }

    override fun assertDtoPropsExpectedMatch(dto: TypeVarianceDto, dao: TypeVarianceDao) {
        assertEquals(dto.typeVariableDeclaration.id, dao.typeVariableDeclaration.id.value)
        assertEquals(dto.varianceCardinality, dao.varianceCardinality.toDto())
    }

}