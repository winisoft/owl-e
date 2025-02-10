package com.wini.owle.services

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.BaseDbMapper
import com.wini.owle.repo.OwleCompositeIdRepo
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.id.CompositeID
//
//abstract class BaseCompositeIdServiceImpl<Dto: BaseDto, T> (
//    private val repository: OwleCompositeIdRepo<T>,
//    private val mapper: BaseDbMapper<Dto, T>
//) : CompositeIdService<Dto> where T: CompositeEntity {
//
//    fun T.toDto(): Dto {
//        return mapper.run { toDto() }
//    }
//
//    fun Dto.toEntity(): T {
//        return mapper.run { toEntity() }
//    }
//
//    override fun getAll(): AsyncResult<List<Dto>> {
//        return runCatching {
//            repository.getAll()
//        }.fold(
//            onSuccess = { entities -> AsyncResult.Success(entities.map { it.toDto() }) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to fetch all entities").asFailure()
//            }
//        )
//    }
//
//    override fun getById(id: CompositeID): AsyncResult<Dto?> {
//        return runCatching {
//            //repository.getById(id)
//        }.fold(
//            onSuccess = { entity -> return AsyncResult.Success(null) }, //entity?.let { AsyncResult.Success(it.toDto()) } ?: AsyncResult.Success(null) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to fetch entity by ID").asFailure()
//            }
//        )
//    }
//
//    override fun create(dto: Dto): AsyncResult<Dto> {
//        return runCatching {
//            repository.create(dto.toEntity())
//        }.fold(
//            onSuccess = { entity -> AsyncResult.Success(entity.toDto()) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to create entity").asFailure()
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
//                AsyncError.DatabaseError(exception.message ?: "Unable to create entities").asFailure()
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
//                AsyncError.DatabaseError(exception.message ?: "Unable to update entity").asFailure()
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
//                AsyncError.DatabaseError(exception.message ?: "Unable to update entities").asFailure()
//            }
//        )
//    }
//
//    override fun delete(id: CompositeID): AsyncResult<Boolean> {
//        return runCatching {
//            //repository.delete(id)
//            true
//        }.fold(
//            onSuccess = { AsyncResult.Success(it) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entity").asFailure()
//            }
//        )
//    }
//
//    override fun delete(ids: List<CompositeID>): AsyncResult<Boolean> {
//        return runCatching {
//            //repository.delete(ids)
//            true
//        }.fold(
//            onSuccess = { AsyncResult.Success(it) },
//            onFailure = { exception ->
//                AsyncError.DatabaseError(exception.message ?: "Unable to delete entities").asFailure()
//            }
//        )
//    }
//}