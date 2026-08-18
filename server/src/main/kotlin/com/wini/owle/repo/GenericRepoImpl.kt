package com.wini.owle.repo

import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.SizedIterable
import kotlin.reflect.KProperty1


open class GenericRepoImpl<ID: Any, T>(
    override val entityClass: EntityClass<ID, T>,
    private val transactions: TransactionExecutor = ExposedTransactionExecutor
): OwleRepo<ID, T> where T : Entity<ID> {

    override fun getAll(): List<T> {
        return getAllEagerLoading(emptyList())
    }

    override fun getAll(eagerLoadProps: List<KProperty1<T, Entity<*>>>): List<T> {
        return getAllEagerLoading(eagerLoadProps)
    }

    override fun getById(id: EntityID<ID>): T? {
        return getByIdEagerLoading(id, emptyList())
    }

    override fun getById(id: EntityID<ID>, eagerLoadProps: List<KProperty1<T, Entity<*>>>): T? {
        return getByIdEagerLoading(id, eagerLoadProps)
    }

    override fun getByIds(ids: List<EntityID<ID>>): List<T?> {
        return transactions.run {
            ids.map { id ->
                getByIdWith(entityClass, id.value, emptyList())
            }
        }
    }

    override fun getWhere(predicate: (T) -> Boolean): List<T> {
        return transactions.run {
            entityClass.all().filter(predicate)
        }
    }

    override fun create(entity: T): T {
        transactions.run {
            entity.flush()
        }
        return entity
    }

    override fun create(entities: List<T>): List<T> {
        transactions.run {
            entities.forEach { it.flush() }
        }
        return entities
    }

    override fun update(entity: T): T {
        transactions.run {
            entity.flush()
        }
        return entity
    }

    override fun update(entities: List<T>): List<T> {
        transactions.run {
            entities.forEach { it.flush() }
        }
        return entities
    }

    override fun delete(id: EntityID<ID>) {
        transactions.run {
            entityClass.findById(id.value)?.delete()
        }
    }

    override fun delete(ids: List<EntityID<ID>>) {
        entityClass.find {
            entityClass.table.id inList ids.map {
                it.value
            }
        }.run {
            transactions.run {
                forEach {
                    it.delete()
                }
            }
        }
    }

    private fun getAllEagerLoading(columns: List<KProperty1<T, Entity<*>>>): List<T> {
        return transactions.run {
            getAllWith(entityClass, columns).toList()
        }
    }

    private fun getByIdEagerLoading(id: EntityID<ID>, columns: List<KProperty1<T, Entity<*>>>): T? {
        return transactions.run {
            getByIdWith(entityClass, id.value, columns)
        }
    }

    fun getAllWith(
        entityClass: EntityClass<ID, T>,
        properties: List<KProperty1<T, Entity<*>>>
    ): SizedIterable<T> {
        val query = entityClass.all()
        properties.forEach { prop -> query.with(prop) }
        return query
    }

    fun getByIdWith(
        entityClass: EntityClass<ID, T>,
        id: ID,
        properties: List<KProperty1<T, Entity<*>>> = emptyList()
    ): T? {
        val query = entityClass.all()
        properties.forEach { prop -> query.with(prop) }
        return query.find {
            it.id.value == id
        }
    }
}