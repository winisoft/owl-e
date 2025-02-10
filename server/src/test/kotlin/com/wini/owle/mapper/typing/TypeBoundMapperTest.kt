package com.wini.owle.mapper.typing

import com.wini.owle.dto.DtoTestInstanceFactory.typeBoundDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.TypeBoundsDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeBoundDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.TypeBounds
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeBoundDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance

import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class TypeBoundMapperTest: BaseOwleMapperTest<TypeBoundsDto, TypeBoundDao, TypeBoundsMapper>() {

    private val realMapper by KoinJavaComponent.inject<TypeBoundsMapper>(TypeBoundsMapper::class.java)

    override val implicatedTables: List<IdTable<*>> = listOf(TypeBounds, TypeDeclarations, TypeDefinitions, VersionSpecs)

    override fun instantiateMapper(): TypeBoundsMapper { return realMapper }

    override fun Transaction.dto(id: Long): TypeBoundsDto {
        val typeBoundDao = typeBoundDao()
        val boundTypeDeclarationDao = typeDeclarationDao()
        val typeVariableDeclarationDao = typeDeclarationDao()
        return typeBoundDto(
            id = id,
            boundTypeDeclaration = typeDeclarationDto(boundTypeDeclarationDao.id.value),
            typeVariableDeclaration = typeDeclarationDto(typeVariableDeclarationDao.id.value)
        )
    }

    override fun Transaction.existingDao(): TypeBoundDao {
        return typeBoundDao()
    }

    override fun Transaction.existingDaoDto(): Pair<TypeBoundDao, TypeBoundsDto> {
        val dao = existingDao()
        val boundTypeDeclarationDao = typeDeclarationDao()
        val typeVariableDeclarationDao = typeDeclarationDao()
        val dto = typeBoundDto(
            id = dao.id.value,
            boundTypeDeclaration = typeDeclarationDto(boundTypeDeclarationDao.id.value),
            typeVariableDeclaration = typeDeclarationDto(typeVariableDeclarationDao.id.value)
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: TypeBoundDao, dto: TypeBoundsDto) {
        assertEquals(dao.typeVariableDeclaration.id.value, dto.typeVariableDeclaration.id)
        assertEquals(dao.boundTypeDeclaration.id.value, dto.boundTypeDeclaration.id)
    }

    override fun assertDtoPropsExpectedMatch(dto: TypeBoundsDto, dao: TypeBoundDao) {
        assertEquals(dto.typeVariableDeclaration.id, dao.typeVariableDeclaration.id.value)
        assertEquals(dto.boundTypeDeclaration.id, dao.boundTypeDeclaration.id.value)
    }


}