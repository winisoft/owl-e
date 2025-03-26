package com.wini.owle.repo

import com.wini.owle.entity.getAllWith
import com.wini.owle.entity.getByIdWith
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.reflect.KProperty1


interface OwleRepo<ID: Any, T> where T : Entity<ID> {
    
    val entityClass: EntityClass<ID, T>

    fun getAll(): List<T>

    fun getAll(eagerLoadProps: List<KProperty1<T, Entity<*>>> = emptyList()): List<T>

    fun create(entity: T): T

    fun create(entities: List<T>): List<T>

    fun update(entity: T): T

    fun update(entities: List<T>): List<T>

    fun getById(id: EntityID<ID>): T?

    fun getByIds(ids: List<EntityID<ID>>): List<T?>

    fun getWhere(predicate: (T) -> Boolean): List<T>

    fun getById(id: EntityID<ID>, eagerLoadProps: List<KProperty1<T, Entity<*>>>): T?

    fun delete(id: EntityID<ID>)

    fun delete(ids: List<EntityID<ID>>)

}

abstract class OwleRepoImpl<ID: Any, T>(
    override val entityClass: EntityClass<ID, T>
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
        return transaction {
            ids.map { id ->
                entityClass.getByIdWith(id.value, emptyList())
            }
        }
    }

    override fun getWhere(predicate: (T) -> Boolean): List<T> {
        return transaction {
            entityClass.all().filter(predicate)
        }
    }

    override fun create(entity: T): T {
        transaction {
            entity.flush()
        }
        return entity
    }

    override fun create(entities: List<T>): List<T> {
        transaction {
            entities.forEach { it.flush() }
        }
        return entities
    }

    override fun update(entity: T): T {
        transaction {
            entity.flush()
        }
        return entity
    }

    override fun update(entities: List<T>): List<T> {
        transaction {
            entities.forEach { it.flush() }
        }
        return entities
    }

    override fun delete(id: EntityID<ID>) {
        transaction {
            entityClass.findById(id.value)?.delete()
        }
    }

    override fun delete(ids: List<EntityID<ID>>) {
        entityClass.find {
            entityClass.table.id inList ids.map {
                it.value
            }
        }.run {
            transaction {
                forEach {
                    it.delete()
                }
            }
        }
    }

    private fun getAllEagerLoading(columns: List<KProperty1<T, Entity<*>>>): List<T> {
        return transaction {
            entityClass.getAllWith(columns).toList()
        }
    }

    private fun getByIdEagerLoading(id: EntityID<ID>, columns: List<KProperty1<T, Entity<*>>>): T? {
        return transaction {
            entityClass.getByIdWith(id.value, columns)
        }
    }
}