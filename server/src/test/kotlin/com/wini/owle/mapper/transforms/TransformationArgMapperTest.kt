package com.wini.owle.mapper.transforms

import com.wini.owle.dto.TransformationArgDto
import com.wini.owle.dto.DtoTestInstanceFactory.transformationArgDto
import com.wini.owle.dto.DtoTestInstanceFactory.transformationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.entity.EntityTestInstanceFactory.transformationArgDao
import com.wini.owle.entity.EntityTestInstanceFactory.transformationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.transforms.TransformationArgDao
import com.wini.owle.mapper.BaseOwleMapperTest
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance

import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class TransformationArgMapperTest: BaseOwleMapperTest<TransformationArgDto, TransformationArgDao, TransformationArgsMapper>() {

    private val realMapper by KoinJavaComponent.inject<TransformationArgsMapper>(TransformationArgsMapper::class.java)

    override fun instantiateMapper(): TransformationArgsMapper { return realMapper }

    override fun Transaction.dto(id: Long): TransformationArgDto {
        val transformTypeDecDao = typeDeclarationDao()
        val transformTypeDecDto = typeDeclarationDto(
            id = transformTypeDecDao.id.value
        )
        val transformReturnDecDao = typeDeclarationDao()
        val transformReturnDecDto = typeDeclarationDto(id = transformReturnDecDao.id.value)
        val transformationDao = transformationDao(returnsTypeDeclaration = transformReturnDecDao)
        val typeDeclarationDao = typeDeclarationDao()
        val typeDefinitionDao = typeDefinitionDao()
        return transformationArgDto(
            id = id,
            transformation = transformationDto(id = transformationDao.id.value, returnsType = transformTypeDecDto),
            typeDeclaration = typeDeclarationDto(
                id = typeDeclarationDao.id.value,
                typeDefinition = typeDefinitionDto(id = typeDefinitionDao.id.value)
            )
        )
    }

    override fun Transaction.existingDao(): TransformationArgDao {
        return transformationArgDao()
    }

    override fun Transaction.existingDaoDto(): Pair<TransformationArgDao, TransformationArgDto> {
        val dao = existingDao()
        val dto = transformationArgDto(id = dao.id.value)
        return dao to dto
    }

    override fun assertDaoPropsExpectedMatch(dao: TransformationArgDao, dto: TransformationArgDto) {
        assertEquals(dao.transformation.id.value, dto.transformation.id)
        assertEquals(dao.typeDeclaration.id.value, dto.typeDeclaration.id)
        assertEquals(dao.name, dto.name)
        assertEquals(dao.description, dto.description)
    }

    override fun assertDtoPropsExpectedMatch(dto: TransformationArgDto, dao: TransformationArgDao) {
        assertEquals(dto.transformation.id, dao.transformation.id.value)
        assertEquals(dto.typeDeclaration.id, dao.typeDeclaration.id.value)
        assertEquals(dto.name, dao.name)
        assertEquals(dto.description, dao.description)
    }

}