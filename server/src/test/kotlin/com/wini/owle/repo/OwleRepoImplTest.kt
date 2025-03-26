package com.wini.owle.repo

import com.wini.owle.ExposedTestUtils
import io.mockk.*
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty1
import kotlin.test.assertEquals

class OwleRepoImplTest {

    // Test implementation of the abstract class
    private class TestEntity(id: EntityID<Long>) : Entity<Long>(id) {
        companion object : EntityClass<Long, TestEntity>(mockk())
        var name: String = ""
    }

    private class TestOwleRepo : OwleRepoImpl<Long, TestEntity>() {
        override val entityClass: EntityClass<Long, TestEntity> = TestEntity
        override fun idExtractor(entity: TestEntity): Long = entity.id.value
    }

    private lateinit var mockEntityClass: EntityClass<Long, TestEntity>
    private lateinit var repository: TestOwleRepo

    @BeforeEach
    fun setup() {
        mockEntityClass = mockk<EntityClass<Long, TestEntity>>()
        ExposedTestUtils.setupMockTransactions()

        repository = spyk(TestOwleRepo())
        every { repository.entityClass } returns mockEntityClass
    }

    @Test
    fun `getAll returns all entities`() {
        // Given
        val entity1 = mockk<TestEntity>()
        val entity2 = mockk<TestEntity>()
        val entities = listOf(entity1, entity2)

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> List<TestEntity>>())
        } answers {
            entities
        }

        // Execute
        val result = repository.getAll()

        // Verify
        assertEquals(2, result.size)
        assertEquals(entities, result)
    }

    @Test
    fun `getAll with eager loading returns all entities with loaded properties`() {
        // Given
        val entity1 = mockk<TestEntity>()
        val entity2 = mockk<TestEntity>()
        val entities = listOf(entity1, entity2)
        val mockProp = mockk<KProperty1<TestEntity, Entity<*>>>()

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> List<TestEntity>>())
        } answers {
            entities
        }

        // Execute
        val result = repository.getAll(listOf(mockProp))

        // Verify
        assertEquals(2, result.size)
        assertEquals(entities, result)
    }

    @Test
    fun `getById returns entity with matching id`() {
        // Given
        val entityId = mockk<EntityID<Long>>()
        val entity = mockk<TestEntity>()

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> TestEntity?>())
        } answers {
            entity
        }

        // Execute
        val result = repository.getById(entityId)

        // Verify
        assertEquals(entity, result)
    }

    @Test
    fun `getById with eager loading returns entity with loaded properties`() {
        // Given
        val entityId = mockk<EntityID<Long>>()
        val entity = mockk<TestEntity>()
        val mockProp = mockk<KProperty1<TestEntity, Entity<*>>>()

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> TestEntity?>())
        } answers {
            entity
        }

        // Execute
        val result = repository.getById(entityId, listOf(mockProp))

        // Verify
        assertEquals(entity, result)
    }

    @Test
    fun `getByIds returns entities with matching ids`() {
        // Given
        val entityId1 = mockk<EntityID<Long>>()
        val entityId2 = mockk<EntityID<Long>>()
        val entity1 = mockk<TestEntity>()
        val entity2 = mockk<TestEntity>()

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> List<TestEntity?>>())
        } answers {
            listOf(entity1, entity2)
        }

        // Execute
        val result = repository.getByIds(listOf(entityId1, entityId2))

        // Verify
        assertEquals(2, result.size)
        assertEquals(entity1, result[0])
        assertEquals(entity2, result[1])
    }

    @Test
    fun `getWhere returns entities matching predicate`() {
        // Given
        val entity1 = mockk<TestEntity>()
        val entity2 = mockk<TestEntity>()

        every { entity1.name } returns "test1"
        every { entity2.name } returns "test2"

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> List<TestEntity>>())
        } answers {
            listOf(entity1)
        }

        // Execute
        val result = repository.getWhere { it.name == "test1" }

        // Verify
        assertEquals(1, result.size)
        assertEquals(entity1, result[0])
    }

    @Test
    fun `create persists a single entity`() {
        // Given
        val entity = mockk<TestEntity>(relaxed = true)

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> Unit>())
        } just runs

        // Execute
        val result = repository.create(entity)

        // Verify
        assertEquals(entity, result)
        verify { transaction(any(), any<Transaction.() -> Unit>()) }
    }

    @Test
    fun `create persists multiple entities`() {
        // Given
        val entity1 = mockk<TestEntity>(relaxed = true)
        val entity2 = mockk<TestEntity>(relaxed = true)
        val entities = listOf(entity1, entity2)

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> Unit>())
        } just runs

        // Execute
        val result = repository.create(entities)

        // Verify
        assertEquals(entities, result)
        verify { transaction(any(), any<Transaction.() -> Unit>()) }
    }

    @Test
    fun `update persists changes to a single entity`() {
        // Given
        val entity = mockk<TestEntity>(relaxed = true)

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> Unit>())
        } just runs

        // Execute
        val result = repository.update(entity)

        // Verify
        assertEquals(entity, result)
        verify { transaction(any(), any<Transaction.() -> Unit>()) }
    }

    @Test
    fun `update persists changes to multiple entities`() {
        // Given
        val entity1 = mockk<TestEntity>(relaxed = true)
        val entity2 = mockk<TestEntity>(relaxed = true)
        val entities = listOf(entity1, entity2)

        // When/Then
        every {
            transaction(any(), captureLambda<Transaction.() -> Unit>())
        } just runs

        // Execute
        val result = repository.update(entities)

        // Verify
        assertEquals(entities, result)
        verify { transaction(any(), any<Transaction.() -> Unit>()) }
    }

    @Test
    fun `delete removes entity with specified id`() {
        // Given
        val entityId = mockk<EntityID<Long>>()
        val entity = mockk<TestEntity>()

        // When/Then
        every { entityId.value } returns 1L
        every { mockEntityClass.findById(1L) } returns entity
        every { entity.delete() } just runs

        every {
            transaction(any(), captureLambda<Transaction.() -> Unit>())
        } just runs

        // Execute
        repository.delete(entityId)

        // Verify
        verify { transaction(any(), any<Transaction.() -> Unit>()) }
    }

    @Test
    fun `delete removes entities with specified ids`() {
        // Given
        val entityId1 = mockk<EntityID<Long>>()
        val entityId2 = mockk<EntityID<Long>>()
        val entity1 = mockk<TestEntity>()
        val entity2 = mockk<TestEntity>()
        val entityIds = listOf(entityId1, entityId2)

        // When/Then
        every { entityId1.value } returns 1L
        every { entityId2.value } returns 2L

        val mockFind = mockk<SizedIterable<TestEntity>>()
        every { mockEntityClass.find(any<SqlExpressionBuilder.() -> Op<Boolean>>()) } returns mockFind
        every { mockFind.forEach(any()) } answers {
            val callback = arg<(TestEntity) -> Unit>(0)
            callback(entity1)
            callback(entity2)
        }

        every { entity1.delete() } just runs
        every { entity2.delete() } just runs

        every {
            transaction(any(), captureLambda<Transaction.() -> Unit>())
        } just runs

        // Execute
        repository.delete(entityIds)

        // Verify
        verify { transaction(any(), any<Transaction.() -> Unit>()) }
        verify { entity1.delete() }
        verify { entity2.delete() }
    }
}