package com.wini.owle.repo.user

import com.wini.owle.entity.*
import com.wini.owle.entity.EntityTestInstanceFactory.tagDao
import com.wini.owle.entity.EntityTestInstanceFactory.userDao
import com.wini.owle.entity.user.TagDao
import com.wini.owle.repo.UnconstrainedDeleteRepoTest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent


open class TagRepositoryTest : DbLayerIntegrationTests<Long, TagDao>() {

    private val realRepo by KoinJavaComponent.inject<TagRepository>(TagRepository::class.java)

    override fun getRepo() = realRepo

    override val implicatedTables = listOf(Tags, TagRelations, Users, StepTags, WorkflowTags)

    override val testModule: Module
        get() = module {
            single<TagRepository> { TagRepository() }
        }

    override fun getInstance(): TagDao {
        return tagDao()
    }

    @Nested
    @DisplayName("Parent / Child Relationships")
    inner class ParentChildTagTest {
        @Test
        fun `test Tags assigned children retain the relationship`() {
            transaction {
                val parent1 = tagDao(
                    name = "Parent Tag 1",
                    description = "Description for Parent Tag 1",
                    user = userDao(email = "jim@jim.com")
                )
                val parent2 = tagDao(
                    name = "Parent Tag 2",
                    description = "Description for Parent Tag 2",
                    user = userDao(email = "jon@jon.com")
                )
                val child = tagDao(
                    name = "Child Tag",
                    description = "Description for Child Tag",
                    user = userDao(email = "jan@jan.com")
                )
                TagRelations.insert {
                    it[parentTag] = parent1.id
                    it[childTag] = child.id
                }
                TagRelations.insert {
                    it[parentTag] = parent2.id
                    it[childTag] = child.id
                }

                val childParents = child.parentTags
                assertEquals(2, childParents.asIterable().count(), "Child tag should have 2 parent tags")
                assertTrue(childParents.any { it.name == "Parent Tag 1" })
                assertTrue(childParents.any { it.name == "Parent Tag 2" })
            }
        }

        @Test
        fun `test Tag assigned as child represents its parent`() {
            transaction {
                val parent1 = tagDao(
                    name = "Parent Tag 1",
                    description = "Description for Parent Tag 1",
                    user = userDao(email = "jim@jim.com")
                )
                val parent2 = tagDao(
                    name = "Parent Tag 2",
                    description = "Description for Parent Tag 2",
                    user = userDao(email = "jon@jon.com")
                )
                val child = tagDao(
                    name = "Child Tag",
                    description = "Description for Child Tag",
                    user = userDao(email = "jan@jan.com")
                )
                TagRelations.insert {
                    it[parentTag] = parent1.id
                    it[childTag] = child.id
                }
                TagRelations.insert {
                    it[parentTag] = parent2.id
                    it[childTag] = child.id
                }

                val childParents = child.parentTags
                assertEquals(2, childParents.asIterable().count(), "Child tag should have 2 parent tags")
                assertTrue(childParents.any { it.name == "Parent Tag 1" })
                assertTrue(childParents.any { it.name == "Parent Tag 2" })
            }
        }

        @Test
        fun `test Tag relationships reflect their proper parent-child relationship`() {
            transaction {
                val parent1 = tagDao(
                    name = "Parent Tag 1",
                    description = "Description for Parent Tag 1",
                    user = userDao(email = "jim@jim.com")
                )
                val parent2 = tagDao(
                    name = "Parent Tag 2",
                    description = "Description for Parent Tag 2",
                    user = userDao(email = "jon@jon.com")
                )
                val child = tagDao(
                    name = "Child Tag",
                    description = "Description for Child Tag",
                    user = userDao(email = "jan@jan.com")
                )
                TagRelations.insert {
                    it[parentTag] = parent1.id
                    it[childTag] = child.id
                }
                TagRelations.insert {
                    it[parentTag] = parent2.id
                    it[childTag] = child.id
                }

                // Assert parent relationships
                val parent1Children = parent1.childTags
                assertEquals(1, parent1Children.asIterable().count(), "Parent 1 should have 1 child tag")
                assertEquals("Child Tag", parent1Children.first().name)

                val parent2Children = parent2.childTags
                assertEquals(1, parent2Children.asIterable().count(), "Parent 2 should have 1 child tag")
                assertEquals("Child Tag", parent2Children.first().name)
            }
        }
    }
}