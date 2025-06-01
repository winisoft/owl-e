package com.wini.owle.services

import com.wini.owle.error.NoSuchIdError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.error.GenericError
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.OwleRepo
import org.jetbrains.exposed.v1.dao.Entity


open class GenericServiceImpl<ID: Any, DTO: BaseDto, E: Entity<ID>>(
    private val repository: OwleRepo<ID, E>,
    private val mapper: DbMapper<ID, DTO, E>,
    private val daoDelegate: DaoDelegate<ID, DTO, E>
) : Service<ID, DTO> {

    override fun create(dto: DTO): AsyncResult<DTO> {
        return runCatching {
            val entity = mapper.toEntity(dto)
            repository.create(entity)
        }.fold(
            onSuccess = { entity ->
                val data = mapper.toDto(entity)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Unable to create entity").asFailure()
            }
        )
    }

    override fun getAll(): AsyncResult<List<DTO>> {
        return runCatching {
            repository.getAll(emptyList())
        }.fold(
            onSuccess = { entities ->
                val data = mapper.toDtos(entities)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Unable to get all entities").asFailure()
            }
        )
    }

    override fun getById(id: ID): AsyncResult<DTO> {
        return runCatching {
            val entityId = daoDelegate.buildEntityId(id)
            repository.getById(entityId)
        }.fold(
            onSuccess = { entity ->
                entity
                    ?.let {
                        val data = mapper.toDto(it)
                        AsyncResult.Success(data)
                    } ?: NoSuchIdError.asFailure()
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Failed to get id $id").asFailure()
            }
        )
    }

    override fun getWhere(predicate: (DTO) -> Boolean): AsyncResult<List<DTO>> {
        return runCatching {
            repository.getWhere { e ->
                mapper.toDto(e).run(predicate)
            }.map(mapper::toDto)
        }.fold(
            onSuccess  = { AsyncResult.Success(it) },
            onFailure  = { e ->
                GenericError(e.message ?: "Failed to getWhere").asFailure()
            }
        )
    }

    override fun create(dtos: List<DTO>): AsyncResult<List<DTO>> {
        return runCatching {
            val entities = mapper.toEntities(dtos)
            repository.create(entities)
        }.fold(
            onSuccess = { entities ->
                val data = mapper.toDtos(entities)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Unable to create entities").asFailure()
            }
        )
    }

    override fun update(dto: DTO): AsyncResult<DTO> {
        return runCatching {
            val entity = mapper.toEntity(dto)
            repository.update(entity)
        }.fold(
            onSuccess = { entity ->
                val data = mapper.toDto(entity)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }

    override fun update(dtos: List<DTO>): AsyncResult<List<DTO>> {
        return runCatching {
            val entities = mapper.toEntities(dtos)
            repository.update(entities)
        }.fold(
            onSuccess = { entities ->
                val data = mapper.toDtos(entities)
                AsyncResult.Success(data)
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }

    override fun delete(id: ID): AsyncResult<Unit> {
        return runCatching {
            val entityId = daoDelegate.buildEntityId(id)
            repository.delete(entityId)
        }.fold(
            onSuccess = {
                AsyncResult.Success(Unit)
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Unable to delete entity $id").asFailure()
            }
        )
    }

    override fun delete(ids: List<ID>): AsyncResult<Unit> {
        return runCatching {
            val entityIds = ids.map { daoDelegate.buildEntityId(it) }
            repository.delete(entityIds)
        }.fold(
            onSuccess = {
                AsyncResult.Success(it)
            },
            onFailure = { exception ->
                GenericError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }
}