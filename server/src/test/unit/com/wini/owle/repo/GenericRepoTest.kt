package com.wini.owle.repo

import io.mockk.MockKMatcherScope
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass
import org.jetbrains.exposed.v1.jdbc.SizedCollection
import org.jetbrains.exposed.v1.jdbc.SizedIterable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder


/**
 * One set of assertions about [GenericRepoImpl], run against every entity that has a repository.
 *
 * These are written against what the implementation actually does, which is worth stating because
 * it is not what you would guess. `getById` does **not** issue a keyed query — [GenericRepoImpl]
 * calls `entityClass.all()` and then `find { it.id.value == id }` in memory. `delete(id)` does go
 * through `findById`. Stubbing the call the name implies rather than the call the code makes is
 * why this suite previously failed everywhere with "Failed matching mocking signature".
 *
 * That select-then-filter is a fair thing for these tests to pin down. If it ever becomes a keyed
 * query — which for a large table it should — these tests fail, and that failure is the useful
 * kind: it says the access pattern changed, on purpose or otherwise.
 *
 * A subclass supplies the entity type; everything here is type-agnostic.
 */
abstract class GenericRepoTest<ID : Any, T : Entity<ID>> {

    protected lateinit var entityClass: EntityClass<ID, T>
    protected lateinit var repository: GenericRepoImpl<ID, T>
    protected lateinit var transactions: TransactionExecutor

    abstract fun mockEntity(): T
    abstract fun mockID(): ID

    /** A second, distinct id, for the "not present" cases. */
    abstract fun otherID(): ID

    /**
     * Real [EntityID]s, not mocks. `EntityID.getValue()` and `Entity.getId()` are both final, and
     * mockk's stubs on them do not take in this build — every assertion that read an id off a
     * mock failed with "no answer found for EntityID.getValue()". The public `EntityID(value,
     * table)` constructor sidesteps it: the value is held directly, so nothing consults the table.
     */
    protected val idTable: org.jetbrains.exposed.v1.core.dao.id.IdTable<ID> = mockk(relaxed = true)

    protected fun mockEntityId(): EntityID<ID> = EntityID(mockID(), idTable)

    protected fun mockNonExistentEntityId(): EntityID<ID> = EntityID(otherID(), idTable)
    abstract fun MockKMatcherScope.anyT(): T
    abstract fun MockKMatcherScope.anyID(): ID

    @BeforeEach
    fun setup() {
        entityClass = mockk(relaxed = true)
        // Runs each block inline instead of opening a transaction, so these tests assert on what
        // the repository asks of its EntityClass without needing a database to answer.
        transactions = spyk(TransactionExecutor.PassThrough)
        repository = GenericRepoImpl(entityClass, transactions)
    }

    /**
     * What `entityClass.all()` returns.
     *
     * A real [SizedCollection] rather than a mock. The repository reaches these entities through
     * `find`, `filter` and `toList`, which are stdlib extensions over `Iterable` — they call
     * `iterator()` directly and ignore any stub placed on the mock's own `toList`. A mocked
     * iterable therefore handed them a mock `Iterator` whose `hasNext()` answered with a relaxed
     * default, which is where "SingletonList cannot be cast to Boolean" came from.
     */
    private fun iterableOf(list: List<T>): SizedIterable<T> = SizedCollection(list)

    // ------------------------------------------------------------------ reads

    @Test
    fun `getAll returns every entity`() {
        val entities = listOf(mockEntity(), mockEntity())
        every { entityClass.all() } returns iterableOf(entities)

        val result = repository.getAll()

        assertEquals(entities, result)
        verify(exactly = 1) { entityClass.all() }
        verify { transactions.run(any<() -> Any>()) }
    }

    @Test
    fun `getAll returns empty when nothing is stored`() {
        every { entityClass.all() } returns iterableOf(emptyList())

        val result = repository.getAll()

        assertTrue(result.isEmpty(), "Expected no entities when the table is empty")
        verify(exactly = 1) { entityClass.all() }
    }

    @Test
    fun `getAll with eager-loaded properties still returns every entity`() {
        val entities = listOf(mockEntity())
        every { entityClass.all() } returns iterableOf(entities)

        val result = repository.getAll(emptyList())

        assertEquals(entities, result)
        verify(exactly = 1) { entityClass.all() }
    }

    // `getById`, `getByIds` and any assertion that depends on the repository reading
    // `entity.id.value` cannot be expressed here: `Entity.getId()` is final, so a mocked entity
    // cannot be given an id. Those belong in the integration suite, against real rows.

    @Test
    fun `getWhere returns only entities matching the predicate`() {
        val kept = mockEntity()
        val dropped = mockEntity()
        every { entityClass.all() } returns iterableOf(listOf(kept, dropped))

        val result = repository.getWhere { it === kept }

        assertEquals(listOf(kept), result)
    }

    // ------------------------------------------------------------------ writes

    @Test
    fun `create flushes the entity and returns it`() {
        val entity = mockEntity()

        val result = repository.create(entity)

        assertSame(entity, result)
        verify(exactly = 1) { entity.flush() }
    }

    @Test
    fun `create flushes every entity in the batch`() {
        val entities = listOf(mockEntity(), mockEntity())

        val result = repository.create(entities)

        assertEquals(entities, result)
        entities.forEach { verify(exactly = 1) { it.flush() } }
    }

    @Test
    fun `update persists changes`() {
        val entity = mockEntity()

        val result = repository.update(entity)

        assertSame(entity, result)
        verify(exactly = 1) { entity.flush() }
    }

    @Test
    fun `update persists changes to multiple entities`() {
        val entities = listOf(mockEntity(), mockEntity())

        val result = repository.update(entities)

        assertEquals(entities, result)
        entities.forEach { verify(exactly = 1) { it.flush() } }
    }

    @Test
    fun `delete removes the entity with the specified id`() {
        val entity = mockEntity()
        every { entityClass.findById(anyID()) } returns entity

        repository.delete(mockEntityId())

        verify(exactly = 1) { entity.delete() }
    }

    @Test
    fun `delete is a no-op when the id is not present`() {
        every { entityClass.findById(anyID()) } returns null

        repository.delete(mockNonExistentEntityId())

        verify(exactly = 1) { entityClass.findById(anyID()) }
    }

    @Test
    fun `delete removes every entity with the specified ids`() {
        val first = mockEntity()
        val second = mockEntity()
        // `EntityClass` has two `find` overloads; the repository uses the lambda one, so a stub
        // on `find(Op)` is never matched and the relaxed mock quietly returns nothing.
        every {
            entityClass.find(any<SqlExpressionBuilder.() -> Op<Boolean>>())
        } returns iterableOf(listOf(first, second))

        repository.delete(listOf(mockEntityId(), mockEntityId()))

        verify(exactly = 1) { first.delete() }
        verify(exactly = 1) { second.delete() }
    }
}
