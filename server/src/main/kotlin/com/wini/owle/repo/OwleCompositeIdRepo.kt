package com.wini.owle.repo

import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import kotlin.reflect.KProperty1


interface OwleCompositeIdRepo<T> : OwleRepo<CompositeID, T> where T : CompositeEntity {

    override fun idExtractor(entity: T): CompositeID {
        return entity.id.value
    }
}

abstract class OwleCompositeIdRepoImpl<T: CompositeEntity>: OwleRepoImpl<CompositeID, T>(), OwleCompositeIdRepo<T>