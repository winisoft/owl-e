package com.wini.owle.mapper.typing

import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.TypeDefinitionDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.EntityTestInstanceFactory.versionSpecDao
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class TypeDefinitionMapperTest: BaseOwleMapperTest<TypeDefinitionDto, TypeDefinitionDao, TypeDefinitionsMapper>() {

    private val realMapper by KoinJavaComponent.inject<TypeDefinitionsMapper>(TypeDefinitionsMapper::class.java)

    override fun instantiateMapper(): TypeDefinitionsMapper { return realMapper }

    override fun Transaction.dto(id: Long): TypeDefinitionDto {
        val versionSpecDao = versionSpecDao()
        val versionSpecDto = versionSpecDto(
            id = versionSpecDao.id.value
        )
        return typeDefinitionDto(
            id = id,
            name = "TestType",
            namespace = "TestNamespace",
            versionSpec = versionSpecDto
        )
    }

    override fun Transaction.existingDao(): TypeDefinitionDao {
        return typeDefinitionDao(name = "TestType", namespace = "TestNamespace")
    }

    override fun Transaction.existingDaoDto(): Pair<TypeDefinitionDao, TypeDefinitionDto> {
        val dao = existingDao()
        val versionSpecDao = versionSpecDao()
        val versionSpecDto = versionSpecDto(
            id = versionSpecDao.id.value
        )
        val dto = typeDefinitionDto(
            id = dao.id.value,
            name = "TestType",
            namespace = "TestNamespace",
            versionSpec = versionSpecDto
        )
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: TypeDefinitionDao, dto: TypeDefinitionDto) {
        assertEquals(dao.name, dto.name)
        assertEquals(dao.namespace, dto.namespace)
    }

    override fun assertDtoPropsExpectedMatch(dto: TypeDefinitionDto, dao: TypeDefinitionDao) {
        assertEquals(dto.name, dao.name)
        assertEquals(dto.namespace, dao.namespace)
    }


}