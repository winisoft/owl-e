package com.wini.owle.mapper.typing

import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.TypeDeclarationDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.versionSpecDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class TypeDeclarationMapperTest: BaseOwleMapperTest<TypeDeclarationDto, TypeDeclarationDao, TypeDeclarationsMapper>() {

    private val realMapper by KoinJavaComponent.inject<TypeDeclarationsMapper>(TypeDeclarationsMapper::class.java)

    override fun instantiateMapper(): TypeDeclarationsMapper { return realMapper }

    override fun Transaction.dto(id: Long): TypeDeclarationDto {
        val typeDefinitionDao = typeDefinitionDao()
        val versionSpecDao = versionSpecDao()
        val versionSpecDto = versionSpecDto(id = versionSpecDao.id.value)
        return typeDeclarationDto(
            id = id,
            typeDefinition = typeDefinitionDto(
                id = typeDefinitionDao.id.value,
                name = typeDefinitionDao.name,
                namespace = typeDefinitionDao.namespace,
                versionSpec = versionSpecDto
            )
        )
    }

    override fun Transaction.existingDao(): TypeDeclarationDao {
        return typeDeclarationDao()
    }

    override fun Transaction.existingDaoDto(): Pair<TypeDeclarationDao, TypeDeclarationDto> {
        val dao = existingDao()
        val typeDefinitionDao = typeDefinitionDao()
        val versionSpecDao = versionSpecDao()
        val versionSpecDto = versionSpecDto(id = versionSpecDao.id.value)
        val dto = typeDeclarationDto(
            id = dao.id.value,
            typeDefinition = typeDefinitionDto(
                id = typeDefinitionDao.id.value,
                name = typeDefinitionDao.name,
                namespace = typeDefinitionDao.namespace,
                versionSpec = versionSpecDto
            )
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: TypeDeclarationDao, dto: TypeDeclarationDto) {
        assertEquals(dao.typeDefinition.name, dto.typeDefinition.name)
        assertEquals(dao.typeDefinition.namespace, dto.typeDefinition.namespace)
    }

    override fun assertDtoPropsExpectedMatch(dto: TypeDeclarationDto, dao: TypeDeclarationDao) {
        assertEquals(dto.typeDefinition.name, dao.typeDefinition.name)
        assertEquals(dto.typeDefinition.namespace, dao.typeDefinition.namespace)
    }

}