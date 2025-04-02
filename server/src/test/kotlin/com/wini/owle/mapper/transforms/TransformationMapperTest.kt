package com.wini.owle.mapper.transforms

import com.wini.owle.dto.DtoTestInstanceFactory.transformationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeDefinitionDto
import com.wini.owle.dto.DtoTestInstanceFactory.versionSpecDto
import com.wini.owle.dto.TransformationDto
import com.wini.owle.entity.EntityTestInstanceFactory.transformationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.EntityTestInstanceFactory.typeDefinitionDao
import com.wini.owle.entity.transforms.TransformationDao
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals

//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class TransformationMapperTest: BaseOwleMapperTest<TransformationDto, TransformationDao, TransformationsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<TransformationsMapper>(TransformationsMapper::class.java)
//
//    override fun instantiateMapper(): TransformationsMapper { return realMapper }
//
//    override fun Transaction.dto(id: Long): TransformationDto {
//        val transformTypeDecDao = typeDeclarationDao()
//        val transformTypeDefDao = typeDefinitionDao()
//        val transformTypeDefDto = typeDefinitionDto(id = transformTypeDefDao.id.value)
//        val transformTypeDecDto = typeDeclarationDto(
//            id = transformTypeDecDao.id.value,
//            typeDefinition = transformTypeDefDto
//        )
//        val transformReturnDecDao = typeDeclarationDao()
//        val transformReturnDecDto = typeDeclarationDto(id = transformReturnDecDao.id.value)
//        val transformationDao = transformationDao()
//        return transformationDto(id = transformationDao.id.value)
//    }
//
//    override fun Transaction.existingDao(): TransformationDao {
//        return transformationDao()
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<TransformationDao, TransformationDto> {
//        val dao = existingDao()
//        val typeDefDao = dao.returnsTypeDeclaration?.typeDefinition
//        val versionSpecDao = dao.returnsTypeDeclaration?.typeDefinition?.versionSpec
//        val returnsTypeDto = typeDeclarationDto(
//            id = dao.id.value,
//            typeDefinition = typeDefinitionDto(
//                id = typeDefDao?.id?.value!!,
//                name = typeDefDao.name,
//                namespace = typeDefDao.namespace,
//                versionSpec = versionSpecDto(
//                    id = versionSpecDao?.id?.value!!
//                )
//            )
//        )
//        val dto = transformationDto(
//            id = dao.id.value,
//            name = dao.name,
//            description = dao.description,
//            examples = dao.examples,
//            returnsType = returnsTypeDto
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: TransformationDao, dto: TransformationDto) {
//        assertEquals(dao.name, dto.name)
//        assertEquals(dao.description, dto.description)
//        assertEquals(dao.examples, dto.examples)
//        assertEquals(dao.returnsTypeDeclaration?.id?.value, dto.returnsType?.id)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: TransformationDto, dao: TransformationDao) {
//        assertEquals(dto.name, dao.name)
//        assertEquals(dto.description, dao.description)
//        assertEquals(dto.examples, dao.examples)
//        assertEquals(dto.returnsType?.id, dao.returnsTypeDeclaration?.id?.value)
//    }
//
//
//}
