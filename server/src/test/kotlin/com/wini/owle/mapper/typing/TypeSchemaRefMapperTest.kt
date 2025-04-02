package com.wini.owle.mapper.typing

import com.wini.owle.dto.DtoTestInstanceFactory.typeDeclarationDto
import com.wini.owle.dto.DtoTestInstanceFactory.typeSchemaRefDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeSchemaRefDao
import com.wini.owle.dto.TypeSchemaRefDto
import com.wini.owle.entity.EntityTestInstanceFactory.typeDeclarationDao
import com.wini.owle.entity.typing.TypeSchemaRefDao
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import org.junit.jupiter.api.Assertions.assertEquals

//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class TypeSchemaRefMapperTest: BaseOwleMapperTest<TypeSchemaRefDto, TypeSchemaRefDao, TypeSchemaRefsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<TypeSchemaRefsMapper>(TypeSchemaRefsMapper::class.java)
//
//    override fun instantiateMapper(): TypeSchemaRefsMapper { return realMapper }
//
//    override fun Transaction.dto(id: Long): TypeSchemaRefDto {
//        val typeDeclarationDao = typeDeclarationDao()
//        val typeSchemaRefDto = typeSchemaRefDto(
//            id = id,
//            parentRef = null,
//            typeDeclaration = typeDeclarationDto(id = typeDeclarationDao.id.value)
//        )
//        return typeSchemaRefDto
//    }
//
//    override fun Transaction.existingDao(): TypeSchemaRefDao {
//
//        return typeSchemaRefDao()
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<TypeSchemaRefDao, TypeSchemaRefDto> {
//        val dao = existingDao()
//        val typeDeclarationDao = typeDeclarationDao()
//        val dto = typeSchemaRefDto(
//            id = dao.id.value,
//            parentRef = null,
//            typeDeclaration = typeDeclarationDto(id = typeDeclarationDao.id.value)
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: TypeSchemaRefDao, dto: TypeSchemaRefDto) {
//        assertEquals(dao.parentRef?.id?.value, dto.parentRef?.id)
//        assertEquals(dao.name, dto.name)
//        assertEquals(dao.typeDeclaration.id.value, dto.typeDeclaration.id)
//        assertEquals(dao.description, dto.description)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: TypeSchemaRefDto, dao: TypeSchemaRefDao) {
//        assertEquals(dto.parentRef?.id, dao.parentRef?.id?.value)
//        assertEquals(dto.name, dao.name)
//        assertEquals(dto.typeDeclaration.id, dao.typeDeclaration.id.value)
//        assertEquals(dto.description, dao.description)
//    }
//
//
//}