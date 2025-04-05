package com.wini.owle.services

import com.wini.owle.async.AsyncError.DatabaseError
import com.wini.owle.async.AsyncError.NoSuchIdError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.EntityIdFactory
import com.wini.owle.repo.OwleRepo
import org.jetbrains.exposed.dao.Entity


open class BaseServiceImpl<ID: Any, Dto: BaseDto, T: Entity<ID>>(
    private val repository: OwleRepo<ID, T>,
    private val mapper: DbMapper<Dto, T>,
    private val idFactory: EntityIdFactory<ID>
) : Service<ID, Dto> {

    override fun create(dto: Dto): AsyncResult<Dto> {
        return runCatching {
            val entity = mapper.toEntity(dto)
            repository.create(entity)
        }.fold(
            onSuccess = { entity ->
                val data = mapper.toDto(entity)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to create entity").asFailure()
            }
        )
    }

    override fun getAll(): AsyncResult<List<Dto>> {
        return runCatching {
            repository.getAll(emptyList())
        }.fold(
            onSuccess = { entities ->
                val data = mapper.toDtos(entities)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to get all entities").asFailure()
            }
        )
    }

    override fun getById(id: ID): AsyncResult<Dto?> {
        return runCatching {
            val entityID = idFactory.build(id)
            repository.getById(entityID)
        }.fold(
            onSuccess = { entity ->
                entity
                    ?.let {
                        val data = mapper.toDto(it)
                        AsyncResult.Success(data)
                    } ?: NoSuchIdError("No such id: $id").asFailure()
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Failed to get id $id").asFailure()
            }
        )
    }

    override fun create(dtos: List<Dto>): AsyncResult<List<Dto>> {
        return runCatching {
            val entities = mapper.toEntities(dtos)
            repository.create(entities)
        }.fold(
            onSuccess = { entities ->
                val data = mapper.toDtos(entities)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to create entities").asFailure()
            }
        )
    }

    override fun update(dto: Dto): AsyncResult<Dto> {
        return runCatching {
            val entity = mapper.toEntity(dto)
            repository.update(entity)
        }.fold(
            onSuccess = { entity ->
                val data = mapper.toDto(entity)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }

    override fun update(dtos: List<Dto>): AsyncResult<List<Dto>> {
        return runCatching {
            val entities = mapper.toEntities(dtos)
            repository.update(entities)
        }.fold(
            onSuccess = { entities ->
                val data = mapper.toDtos(entities)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }

    override fun delete(id: ID): AsyncResult<Boolean> {
        return runCatching {
            val entityID = idFactory.build(id)
            repository.delete(entityID)
            true
        }.fold(
            onSuccess = {
                AsyncResult.Success(it)
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entity $id").asFailure()
            }
        )
    }

    override fun delete(ids: List<ID>): AsyncResult<Boolean> {
        return runCatching {
            val entityIds = ids.map { idFactory.build(it) }
            repository.delete(entityIds)
            true
        }.fold(
            onSuccess = {
                AsyncResult.Success(it)
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }
}