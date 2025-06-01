package com.wini.owle.repo

import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID
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

