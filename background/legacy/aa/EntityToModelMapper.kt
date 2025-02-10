package com.wini.owle.air.domain.aa

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * When a given table in a SqlDelight database has queries only such that the column wildcard is used -- i.e.
 * SELECT * FROM tableName, as opposed to SELECT id, name FROM tableName, it generates a type whose name matches
 * the name of the table to return results with. When specific columns are requested, however, it generates a model
 * associated with the name of the query, e.g. SelectAll, SelectWhereEnvId, etc.
 *
 * As a consequence, it is always the case that the domain must map a model into an entity -- the entity model is used
 * for the CRUD operations. It is only sometimes the case there even exists a
 */
interface EntityToModelMapper<Entity: Any, Model: Any> {

    fun Entity.mapToModel(): Model

    fun Entity?.mapToModelNullable(): Model? = this?.mapToModel()

    fun List<Entity>.mapToModel(): List<Model> = map { it.mapToModel() }

    fun Flow<Entity?>.mapToModelNullable(): Flow<Model?> = map { it.mapToModelNullable() }

    fun Flow<List<Entity>>.mapToModel(): Flow<List<Model>> = map { it.mapToModel() }

}