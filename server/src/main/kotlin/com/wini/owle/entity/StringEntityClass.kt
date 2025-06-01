package com.wini.owle.entity

import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass


/*
 * Base class for entities with string id
 */
abstract class StringEntityClass<E: Entity<String>>(
    table: IdTable<String>,
    entityType: Class<E>? = null
) : EntityClass<String, E>(table, entityType)
