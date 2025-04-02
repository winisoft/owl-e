package com.wini.owle.mapper.user

import com.wini.owle.dto.DtoTestInstanceFactory.tagDto
import com.wini.owle.dto.TagDto
import com.wini.owle.entity.EntityTestInstanceFactory.tagDao
import com.wini.owle.entity.user.TagDao
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance

import org.koin.java.KoinJavaComponent
//
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class TagsMapperTest: BaseOwleMapperTest<TagDto, TagDao, TagsMapper>() {
//
//    private val realMapper by KoinJavaComponent.inject<TagsMapper>(TagsMapper::class.java)
//
//
//    override fun instantiateMapper(): TagsMapper { return realMapper }
//
//    override fun Transaction.dto(id: Long): TagDto {
//        return tagDto(
//            id = id,
//            name = "Test Tag",
//            description = "A tag for testing"
//        )
//    }
//
//    override fun Transaction.existingDao(): TagDao {
//        return tagDao(
//            name = "Test Tag",
//            description = "A tag for testing"
//        )
//    }
//
//    override fun Transaction.existingDaoDto(): Pair<TagDao, TagDto> {
//        val dao = existingDao()
//        val dto = tagDto(
//            id = dao.id.value,
//            name = dao.name,
//            description = dao.description
//        )
//        return dao to dto
//    }
//
//    override fun assertDaoPropsExpectedMatch(dao: TagDao, dto: TagDto) {
//        assertEquals(dao.name, dto.name)
//        assertEquals(dao.description, dto.description)
//    }
//
//    override fun assertDtoPropsExpectedMatch(dto: TagDto, dao: TagDao) {
//        assertEquals(dto.name, dao.name)
//        assertEquals(dto.description, dao.description)
//    }
//
//
//}