package com.wini.owle.air.domain.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface ModelToEntityMapper<Model: Any, Entity: Any> {

    fun Model.mapToEntity(): Entity

    fun Model?.mapToEntityNullable(): Entity? = this?.mapToEntity()

    fun List<Model>.mapToEntity(): List<Entity> = map { it.mapToEntity() }

    fun Flow<Model?>.mapToEntityNullable(): Flow<Entity?> = map { it.mapToEntityNullable() }

    fun Flow<List<Model>>.mapToEntity(): Flow<List<Entity>> = map { it.mapToEntity() }

}