package com.wini.owle.air.domain.aa

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DbSelectAllMapper<DbType, Model> {

    abstract fun DbType.mapSelectAllToModel(): Model

    fun List<DbType>.mapSelectAllToModel(): List<Model> = map { it.mapSelectAllToModel() }

    fun Flow<List<DbType>>.mapSelectAllToModel(): Flow<List<Model>> = map { it.mapSelectAllToModel() }

}

interface DbSelectByIdMapper<DbType, Model> {

    abstract fun DbType?.mapSelectByIdToModel(): Model?

    fun Flow<DbType?>.mapSelectByIdToModel(): Flow<Model?> = map { it.mapSelectByIdToModel() }


}