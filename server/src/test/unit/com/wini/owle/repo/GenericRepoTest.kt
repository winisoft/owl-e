package com.wini.owle.repo

import io.mockk.*
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.SizedIterable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty1


abstract class GenericRepoTest<ID: Any, T: Entity<ID>> {

    private lateinit var entityClass: EntityClass<ID, T>
    private lateinit var repository: GenericRepoImpl<ID, T>
    private lateinit var transactions: TransactionExecutor

    abstract fun mockEntity(): T
    abstract fun mockEntityId(): EntityID<ID>
    abstract fun mockNonExistentEntityId(): EntityID<ID>
    abstract fun mockID(): ID
    abstract fun MockKMatcherScope.anyT(): T
    abstract fun MockKMatcherScope.anyID(): ID

    @BeforeEach
    fun setup() {
        entityClass = mockk()
        //transactions = mockk()
//        every<Any> { transactions.run(captureLambda()) } answers {
//            firstArg<() -> Any>().invoke()
//        }

        repository = GenericRepoImpl(entityClass)
    }

    /**
     * GIVEN no eager load props
     * WHEN getAll is called
     * THEN returns all entities from entity class
     */
    @Test
    fun `getAll success`() {
        // Given
        val entity = mockEntity()
        val allEntities = mockk<SizedIterable<T>>()
        every { allEntities.toList() } returns listOf(entity)
        every { entityClass.all() } returns allEntities
        every { allEntities.with(any<KProperty1<T, Entity<*>>>()) } returns allEntities

        // When
        val result = repository.getAll()

        // Then
        assertEquals(listOf(entity), result)
        verify { transactions.run(any()) }
        verify { entityClass.all() }
    }

    /**
     * GIVEN eager load props
     * WHEN getAll is called
     * THEN returns all entities with specified props
     */
    @Test
    fun `getAll with eager-loaded FK extension properties success`() {
        // Given
        val entity = mockEntity()
        val allEntities = mockk<SizedIterable<T>>()
        every { allEntities.toList() } returns listOf(entity)
        every { entityClass.all() } returns allEntities
        every { allEntities.with(any<KProperty1<T, Entity<*>>>()) } returns allEntities

        val eagerLoadProps = listOf<KProperty1<T, Entity<*>>>(mockk())

        // When
        val result = repository.getAll(eagerLoadProps)

        // Then
        assertEquals(listOf(entity), result)
        verify { transactions.run(any()) }
        verify { entityClass.all() }
        verify { allEntities.with(eagerLoadProps[0]) }
    }

    /**
     * GIVEN id exists
     * WHEN getById is called
     * THEN returns matching entity
     */
    @Test
    fun `getById success`() {
        // Given
        val entity = mockEntity()
        val id = mockEntityId()
        val allEntities = mockk<SizedIterable<T>>()

        every { entityClass.all() } returns allEntities
        every { allEntities.with(any<KProperty1<T, Entity<*>>>()) } returns allEntities
        every { id.value } returns mockEntity().id.value
        every { 
            allEntities.find(any()) 
        } returns entity

        // When
        val result = repository.getById(id)

        // Then
        assertEquals(entity, result)
        verify { transactions.run(any()) }
        verify { entityClass.all() }
        verify { allEntities.find(any()) }
    }

    /**
     * GIVEN id exists with foreign key related properties
     * WHEN getById is called with eager loading props
     * THEN returns entity with specified props loaded
     */
    @Test
    fun `getById eager-loaded with FK extension props success`() {
        // Given
        val entity = mockEntity()
        val id = mockEntityId()
        val allEntities = mockk<SizedIterable<T>>()
        val eagerLoadProps = listOf<KProperty1<T, Entity<*>>>(mockk())
        
        every { entityClass.all() } returns allEntities
        every { allEntities.with(any<KProperty1<T, Entity<*>>>()) } returns allEntities
        every { id.value } returns mockID()
        every { 
            allEntities.find(any()) 
        } returns entity

        // When
        val result = repository.getById(id, eagerLoadProps)

        // Then
        assertEquals(entity, result)
        verify { transactions.run(any()) }
        verify { entityClass.all() }
        verify { allEntities.with(eagerLoadProps[0]) }
        verify { allEntities.find(any()) }
    }

    /**
     * GIVEN a list of ids exist
     * WHEN getByIds is called with that list of ids
     * THEN matching entities are returned in the same order
     */
    @Test
    fun `getByIds success`() {
        // Given
        val entity1 = mockEntity()
        val entity2 = mockEntity()
        val id1 = mockEntityId()
        val id2 = mockEntityId()
        val allEntities = mockk<SizedIterable<T>>()

        every { entityClass.all() } returns allEntities
        every { allEntities.with(any<KProperty1<T, Entity<*>>>()) } returns allEntities
        every { id1.value } returns mockID()
        every { id2.value } returns mockID()

        // When
        val result = repository.getByIds(listOf(id1, id2))

        // Then
        assertEquals(listOf(entity1, entity2), result)
        verify { transactions.run(any()) }
    }

    /**
     * GIVEN non-existent entity
     * WHEN create is passed that entity
     * THEN a single matching entity is persisted
     */
    @Test
    fun `create entity success`() {
        // Given
        val entity = mockEntity()
        every { entity.flush() } returns true

        // When
        val result = repository.create(entity)

        // Then
        assertEquals(entity, result)
        verify { transactions.run(any()) }
        verify { entity.flush() }
    }

    /**
     * GIVEN a list of non-existent entities
     * WHEN create is called with that list
     * THEN a list of matching entities is persisted
     */
    @Test
    fun `create multiple entities success`() {
        // Given
        val entity1 = mockEntity()
        val entity2 = mockEntity()
        val entities = listOf(entity1, entity2)
        
        every { entity1.flush() } returns true
        every { entity2.flush() } returns true

        // When
        val result = repository.create(entities)

        // Then
        assertEquals(entities, result)
        verify { transactions.run(any()) }
        verify { entity1.flush() }
        verify { entity2.flush() }
    }

    /**
     * GIVEN an entity has been previously persisted
     * WHEN update is passed an entity with matching id but ancillary properties modified
     * THEN the modified entity properties are persisted in the record associated with its id
     */
    @Test
    fun `update persists changes`() {
        // Given
        val entity = mockEntity()
        every { entity.flush() } returns true

        // When
        val result = repository.update(entity)

        // Then
        assertEquals(entity, result)
        verify { transactions.run(any()) }
        verify { entity.flush() }
    }

    /**
     * GIVEN a list of entities has been previously persisted
     * WHEN update is passed a list of entities with matching id but ancillary properties modified
     * THEN the modified entities' properties are persisted in the record associated with each's id
     */
    @Test
    fun `update persists changes to multiple entities`() {
        // Given
        val entity1 = mockEntity()
        val entity2 = mockEntity()
        val entities = listOf(entity1, entity2)
        
        every { entity1.flush() } returns true
        every { entity2.flush() } returns true

        // When
        val result = repository.update(entities)

        // Then
        assertEquals(entities, result)
        verify { transactions.run(any()) }
        verify { entity1.flush() }
        verify { entity2.flush() }
    }

    /**
     * GIVEN an entity has been previously persisted
     * WHEN delete is passed the id that matches it
     * THEN the entity is removed from the Entity types present 'all' set
     */
    @Test
    fun `delete removes entity with specified id`() {
        // Given
        val id = mockEntityId()
        val entity = mockEntity()
        
        every { id.value } returns mockEntityId().value
        every { entityClass.findById(anyID()) } returns entity
        every { entity.delete() } just runs

        // When
        repository.delete(id)

        // Then
        verify { transactions.run(any()) }
        verify { entityClass.findById(anyID()) }
        verify { entity.delete() }
    }

    /**
     * GIVEN an entity has been previously persisted
     * WHEN getWhere is called with a lambda that properly identifies it in its matching predicate
     * THEN the entity is returned
     */
    @Test
    fun `getWhere returns entities matching predicate`() {
        // Given
        val entity = mockEntity()
        val allEntities = mockk<SizedIterable<T>>()
        
        every { entityClass.all() } returns allEntities
        every { allEntities.filter(any()) } returns listOf(entity)

        // When
        val result = repository.getWhere { true }

        // Then
        assertEquals(listOf(entity), result)
        verify { transactions.run(any()) }
        verify { entityClass.all() }
        verify { allEntities.filter(any()) }
    }

    /**
     * GIVEN a list of entities has been previously persisted
     * WHEN delete is called with a list of ids matching these
     * THEN the entities are removed from the 'all' set of existing entities
     */
    @Test
    fun `delete removes entities with specified ids`() {
        // Given
        val id1 = mockEntityId()
        val id2 = mockEntityId()
        val ids = listOf(id1, id2)
        val entities = mockk<SizedIterable<T>>()
        val entity1 = mockEntity()
        val entity2 = mockEntity()
        
        every { id1.value } returns mockID()
        every { id2.value } returns mockID()
        every { entityClass.table.id } returns mockk()
        every { entityClass.find(any<Op<Boolean>>()) } returns entities
        every { entities.forEach(captureLambda<(T) -> Unit>()) } answers {
            lambda<(T) -> Unit>().invoke(entity1)
            lambda<(T) -> Unit>().invoke(entity2)
        }
        every { entity1.delete() } just runs
        every { entity2.delete() } just runs

        // When
        repository.delete(ids)

        // Then
        verify { transactions.run(any()) }
        verify { entityClass.find(any<Op<Boolean>>()) }
        verify { entity1.delete() }
        verify { entity2.delete() }
    }
}
