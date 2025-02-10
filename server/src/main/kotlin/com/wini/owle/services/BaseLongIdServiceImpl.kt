package com.wini.owle.services

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.BaseDbMapper
import com.wini.owle.repo.OwleLongIdRepo
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID
//
//abstract class BaseLongIdServiceImpl<Dto: BaseDto, T>(
//    private val repository: OwleLongIdRepo<T>,
//    private val mapper: BaseDbMapper<Dto, T>
//) : LongIdService<Dto> where T: LongEntity {
//
//    fun T.toDto(): Dto {
//        return mapper.run { toDto() }
//    }
//
//    fun Dto.toEntity(): T {
//        return mapper.run { toEntity() }
//    }
//
//    override fun create(dto: Dto): AsyncResult<Dto> {
//        return runCatching {
//            repository.create(dto.toEntity())
//        }.fold(
//            onSuccess = { entity -> AsyncResult.Success(entity.toDto()) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//
//    override fun getAll(): AsyncResult<List<Dto>> {
//        return runCatching {
//            repository.getAll(emptyList())
//        }.fold(
//            onSuccess = { entities -> AsyncResult.Success(entities.map { it.toDto() }) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//
//    override fun getById(id: Long): AsyncResult<Dto?> {
//        return runCatching {
//            repository.getById(EntityID(id, repository.entityClass.table))
//        }.fold(
//            onSuccess = { entity -> entity?.let { AsyncResult.Success(it.toDto()) } ?: AsyncResult.Success(null) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//
//    override fun create(dtos: List<Dto>): AsyncResult<List<Dto>> {
//        return runCatching {
//            repository.create(dtos.map { it.toEntity() })
//        }.fold(
//            onSuccess = { entities -> AsyncResult.Success(entities.map { it.toDto() }) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//
//    override fun update(dto: Dto): AsyncResult<Dto> {
//        return runCatching {
//            repository.update(dto.toEntity())
//        }.fold(
//            onSuccess = { entity -> AsyncResult.Success(entity.toDto()) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//
//    override fun update(dtos: List<Dto>): AsyncResult<List<Dto>> {
//        return runCatching {
//            repository.update(dtos.map { it.toEntity() })
//        }.fold(
//            onSuccess = { entities -> AsyncResult.Success(entities.map { it.toDto() }) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//
//    override fun delete(id: Long): AsyncResult<Boolean> {
//        return runCatching {
//            repository.delete(EntityID(id, repository.entityClass.table))
//            true
//        }.fold(
//            onSuccess = { AsyncResult.Success(it) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//
//    override fun delete(ids: List<Long>): AsyncResult<Boolean> {
//        return runCatching {
//            repository.delete(ids.map { EntityID(it, repository.entityClass.table) })
//            true
//        }.fold(
//            onSuccess = { AsyncResult.Success(it) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//}