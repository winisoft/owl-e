package com.wini.owle.entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SizedIterable
import kotlin.reflect.KProperty1


fun <ID : Any, T : Entity<ID>> EntityClass<ID, T>.getAllWith(
    properties: List<KProperty1<T, Entity<*>>> = emptyList()
): SizedIterable<T> {
    return properties.fold(this.all()) { query, property ->
        query.with(property)
    }
}

fun <ID : Any, T : Entity<ID>> EntityClass<ID, T>.getByIdWith(
    id: ID,
    properties: List<KProperty1<T, Entity<*>>> = emptyList()
): T? {
    return properties.fold(this.all()) { query, property ->
        query.with(property)
    }.find {
        it.id.value == id
    }
}

//fun <ID : Any, T : Entity<ID>> EntityClass<ID, T>.getByIdWith(
//    id: ID,
//    collectionProperties: List<KProperty1<T, Iterable<Entity<*>>>>
//): T? {
//    return this.findById(id)?.apply {
//        collectionProperties.forEach { prop ->
//            this.load(prop)
//        }
//    }
//}