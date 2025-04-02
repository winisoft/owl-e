package com.wini.owle.services

import com.wini.owle.async.AsyncError.DatabaseError
import com.wini.owle.async.AsyncError.NoSuchIdError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.BaseDbMapper
import com.wini.owle.repo.OwleRepo
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID


open class BaseServiceImpl<ID: Any, Dto: BaseDto, T: Entity<ID>>(
    private val repository: OwleRepo<ID, T>,
    private val mapper: BaseDbMapper<Dto, T>
) : Service<ID, Dto> {

    fun T.toDto(): Dto {
        return mapper.run { toDto() }
    }

    fun Dto.toEntity(): T {
        return mapper.run { toEntity() }
    }

    override fun create(dto: Dto): AsyncResult<Dto> {
        return runCatching {
            repository.create(dto.toEntity())
        }.fold(
            onSuccess = { entity -> AsyncResult.Success(entity.toDto()) },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to create entity").asFailure()
            }
        )
    }

    override fun getAll(): AsyncResult<List<Dto>> {
        return runCatching {
            repository.getAll(emptyList())
        }.fold(
            onSuccess = { entities -> AsyncResult.Success(entities.map { it.toDto() }) },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to get all entities").asFailure()
            }
        )
    }

    override fun getById(id: ID): AsyncResult<Dto?> {
        return runCatching {
            repository.getById(EntityID(id, repository.entityClass.table))
        }.fold(
            onSuccess = { entity ->
                entity?.let {
                    AsyncResult.Success(it.toDto())
                } ?: NoSuchIdError("No such id in ${repository.entityClass.table}: $id").asFailure()
            },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Failed to get id $id").asFailure()
            }
        )
    }

    override fun create(dtos: List<Dto>): AsyncResult<List<Dto>> {
        return runCatching {
            repository.create(dtos.map { it.toEntity() })
        }.fold(
            onSuccess = { entities -> AsyncResult.Success(entities.map { it.toDto() }) },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to create entities").asFailure()
            }
        )
    }

    override fun update(dto: Dto): AsyncResult<Dto> {
        return runCatching {
            repository.update(dto.toEntity())
        }.fold(
            onSuccess = { entity -> AsyncResult.Success(entity.toDto()) },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }

    override fun update(dtos: List<Dto>): AsyncResult<List<Dto>> {
        return runCatching {
            repository.update(dtos.map { it.toEntity() })
        }.fold(
            onSuccess = { entities -> AsyncResult.Success(entities.map { it.toDto() }) },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }

    override fun delete(id: ID): AsyncResult<Boolean> {
        return runCatching {
            repository.delete(EntityID(id, repository.entityClass.table))
            true
        }.fold(
            onSuccess = { AsyncResult.Success(it) },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entity $id").asFailure()
            }
        )
    }

    override fun delete(ids: List<ID>): AsyncResult<Boolean> {
        return runCatching {
            repository.delete(ids.map { EntityID(it, repository.entityClass.table) })
            true
        }.fold(
            onSuccess = { AsyncResult.Success(it) },
            onFailure = { exception ->
                DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
            }
        )
    }
}