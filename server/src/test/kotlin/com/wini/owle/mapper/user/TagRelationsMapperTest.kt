package com.wini.owle.mapper.user

import com.wini.owle.dto.TagRelationDto
import com.wini.owle.entity.*
import com.wini.owle.entity.Tags
import com.wini.owle.entity.user.TagDao
import com.wini.owle.entity.user.TagRelationDao
import com.wini.owle.entity.user.UserDao
import com.wini.owle.mapper.BaseOwleMapperTest
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//open class TagRelationsMapperTest: BaseOwleMapperTest<TagRelationDto, TagRelationDao>() {
//
//    private val mapper = TagRelationsMapper()
//
//    override val implicatedTables: List<IdTable<*>>
//        get() = listOf(Tags, TagRelations, Users, StepTags, WorkflowTags)
//
//    /**
//     * Tests minimal valid data in the toEntity() mapper.
//     */
//    @Test
//    fun testToEntityWithMinimalValidData(): Unit = transaction {
//        // Insert two TagDao records
//        val userDao = UserDao.new {
//            email = "test@example.com"
//            password = "pass"
//        }
//        val parentTagDao = TagDao.new {
//            name = "ParentTag"
//            description = null
//            user = userDao
//        }
//        val childTagDao = TagDao.new {
//            name = "ChildTag"
//            description = "Some child tag"
//            user = userDao
//        }
//
//        // Create a minimal TagRelationDto referencing them
//        val relationDto = TagRelationDto(
//            parentTagId = parentTagDao.id.value,
//            childTagId = childTagDao.id.value
//        )
//
//        // Convert to Entity
//        val relationDao = mapper.run { relationDto.toEntity() }
//        assertEquals(parentTagDao.id.value, relationDao.parentTag.id.value)
//        assertEquals(childTagDao.id.value, relationDao.childTag.id.value)
//    }
//
//    /**
//     * Tests minimal valid data in the toDto() mapper.
//     */
//    @Test
//    fun testToDtoWithMinimalValidData(): Unit = transaction {
//        // Insert two TagDao records
//        val userDao = UserDao.new {
//            email = "test@example.com"
//            password = "pass"
//        }
//        val parentTagDao = TagDao.new {
//            name = "ParentTag"
//            description = "Parent Desc"
//            user = userDao
//        }
//        val childTagDao = TagDao.new {
//            name = "ChildTag"
//            description = "Child Desc"
//            user = userDao
//        }
//
//        // Make a TagRelationDao in memory
//        val relationDao = TagRelationDao.create(parentTagDao, childTagDao)
//
//        // Convert to Dto
//        val relationDto = mapper.run { relationDao.toDto() }
//        assertEquals(parentTagDao.id.value, relationDto.parentTagId)
//        assertEquals(childTagDao.id.value, relationDto.childTagId)
//    }
//
//    /**
//     * Tests a round-trip: Dto → Dao → Dto, ensuring the final Dto
//     * matches the original data.
//     */
//    @Test
//    fun testRoundTripMapping(): Unit = transaction {
//        val userDao = UserDao.new {
//            email = "test@example.com"
//            password = "pass"
//        }
//        val parentTagDao = TagDao.new {
//            name = "ParentTag"
//            description = "RoundTrip Parent"
//            user = userDao
//        }
//        val childTagDao = TagDao.new {
//            name = "ChildTag"
//            description = "RoundTrip Child"
//            user = userDao
//        }
//
//        val originalDto = TagRelationDto(
//            parentTagId = parentTagDao.id.value,
//            childTagId = childTagDao.id.value
//        )
//
//        val dao = mapper.run { originalDto.toEntity() }
//        val roundTrippedDto = mapper.run { dao.toDto() }
//
//        assertEquals(originalDto.parentTagId, roundTrippedDto.parentTagId)
//        assertEquals(originalDto.childTagId, roundTrippedDto.childTagId)
//    }
//
//    /**
//     * Demonstrates testing "null optional fields" logic.
//     * Since TagRelation has no optional fields, we simulate by
//     * giving the child ID as 0L. The mapper should fail.
//     */
//    @Test
//    fun testNullOptionalFieldsOrMissingId(): Unit = transaction {
//        val userDao = UserDao.new {
//            email = "test@example.com"
//            password = "pass"
//        }
//        val parentTagDao = TagDao.new {
//            name = "ParentTag"
//            description = "Null Optional Simulation"
//            user = userDao
//        }
//
//        val relationDto = TagRelationDto(
//            parentTagId = parentTagDao.id.value,
//            childTagId = 0L  // Simulate "null or missing"
//        )
//
//        val ex = assertThrows<IllegalStateException> {
//            mapper.run { relationDto.toEntity() }
//        }
//        assertTrue(ex.message!!.contains("not found"))
//    }
//
//    /**
//     * Tests composite key correctness by verifying we can reference
//     * a relationship's (parentTagId, childTagId) in the TagRelations table.
//     * This simulates an actual insert (if you used a real DAO for TagRelation).
//     */
//    @Test
//    fun testCompositeKeyCorrectness(): Unit = transaction {
//        // Insert parent and child
//        val userDao = UserDao.new {
//            email = "test@example.com"
//            password = "pass"
//        }
//        val parent = TagDao.new {
//            name = "CompositeParent"
//            description = null
//            user = userDao
//        }
//        val child = TagDao.new {
//            name = "CompositeChild"
//            description = null
//            user = userDao
//        }
//
//        // If we had a real TagRelationDao that inserts into TagRelations, we’d do so here.
//        // For demonstration, we'll just verify the IDs can form a valid composite key.
//        val relationDto = TagRelationDto(parent.id.value, child.id.value)
//        val relationDao = mapper.run { relationDto.toEntity() }
//
//        // 'relationDao' references the two TagDao objects.
//        // If you store them in TagRelations, you'd do something like:
//        //   TagRelations.insert { ... }  (not shown).
//        // Check the pair is correct:
//        assertEquals(parent.id.value, relationDao.parentTag.id.value)
//        assertEquals(child.id.value, relationDao.childTag.id.value)
//    }
//
//    /**
//     * Tests relationship referencing by ensuring the TagRelationDao indeed
//     * has references to the correct parent/child TagDao,
//     * which might matter in a hierarchical or self-referential structure.
//     */
//    @Test
//    fun testRelationshipReferencing(): Unit = transaction {
//        val userDao = UserDao.new {
//            email = "test@example.com"
//            password = "pass"
//        }
//        val parentTagDao = TagDao.new {
//            name = "ParentTagRefTest"
//            description = null
//            user = userDao
//        }
//        val childTagDao = TagDao.new {
//            name = "ChildTagRefTest"
//            description = null
//            user = userDao
//        }
//
//        val dto = TagRelationDto(parentTagDao.id.value, childTagDao.id.value)
//        val dao = mapper.run { dto.toEntity() }
//
//        // Confirm the relationship references the real parent/child
//        assertSame(parentTagDao, dao.parentTag)
//        assertSame(childTagDao, dao.childTag)
//    }
//}
