package com.wini.owle.repo

import org.jetbrains.exposed.dao.LongEntity


interface OwleLongIdRepo<T> : OwleRepo<Long, T> where T : LongEntity {

    override fun idExtractor(entity: T): Long {
        return entity.id.value
    }
}

abstract class OwleLongIdRepoImpl<T: LongEntity>: OwleRepoImpl<Long, T>(), OwleLongIdRepo<T>