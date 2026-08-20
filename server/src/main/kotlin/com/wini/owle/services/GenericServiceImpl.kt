package com.wini.owle.services

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.BaseDto
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.error.GenericError
import com.wini.owle.error.NoSuchIdError
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.ExposedTransactionExecutor
import com.wini.owle.repo.OwleRepo
import com.wini.owle.repo.TransactionExecutor
import org.jetbrains.exposed.v1.dao.Entity


/**
 * The service every generated `<Type>Service` extends.
 *
 * **Query and mapping share one transaction, and must.** The repository opens its own transaction
 * and closes it before returning, so an entity mapped afterwards throws "Can't init value outside
 * the transaction" the moment the mapper touches a relation. Every method here used to do exactly
 * that — query inside `runCatching`, map inside `fold`'s `onSuccess` — which is why `/api/workflow`
 * answered 500 while `/api/distribution` answered 200: `Distribution` is a bare string-keyed table
 * with nothing to lazy-load, and every type with a relation failed.
 *
 * Exposed joins a nested `transaction` to the enclosing one, so the repository's internal scope
 * simply nests inside this and no extra connection is taken.
 */
open class GenericServiceImpl<ID : Any, DTO : BaseDto, E : Entity<ID>>(
    private val repository: OwleRepo<ID, E>,
    private val mapper: DbMapper<ID, DTO, E>,
    private val daoDelegate: DaoDelegate<ID, DTO, E>,
    private val transactions: TransactionExecutor = ExposedTransactionExecutor
) : Service<ID, DTO> {

    /** Runs [block] in one transaction and reports any failure as [message]. */
    private inline fun <T> attempt(message: String, crossinline block: () -> T): AsyncResult<T> =
        runCatching { transactions.run { block() } }.fold(
            onSuccess = { AsyncResult.Success(it) },
            onFailure = { GenericError(it.message ?: message).asFailure() }
        )

    override fun create(dto: DTO): AsyncResult<DTO> =
        attempt("Unable to create entity") {
            mapper.toDto(repository.create(mapper.toEntity(dto)))
        }

    override fun create(dtos: List<DTO>): AsyncResult<List<DTO>> =
        attempt("Unable to create entities") {
            mapper.toDtos(repository.create(mapper.toEntities(dtos)))
        }

    override fun getAll(): AsyncResult<List<DTO>> =
        attempt("Unable to get all entities") {
            mapper.toDtos(repository.getAll(emptyList()))
        }

    override fun getById(id: ID): AsyncResult<DTO> =
        runCatching {
            transactions.run {
                repository.getById(daoDelegate.buildEntityId(id))?.let(mapper::toDto)
            }
        }.fold(
            onSuccess = { it?.let { dto -> AsyncResult.Success(dto) } ?: NoSuchIdError.asFailure() },
            onFailure = { GenericError(it.message ?: "Failed to get id $id").asFailure() }
        )

    override fun getWhere(predicate: (DTO) -> Boolean): AsyncResult<List<DTO>> =
        attempt("Failed to getWhere") {
            repository.getWhere { entity -> mapper.toDto(entity).run(predicate) }
                .map(mapper::toDto)
        }

    override fun update(dto: DTO): AsyncResult<DTO> =
        attempt("Unable to update entity") {
            mapper.toDto(repository.update(mapper.toEntity(dto)))
        }

    override fun update(dtos: List<DTO>): AsyncResult<List<DTO>> =
        attempt("Unable to update entities") {
            mapper.toDtos(repository.update(mapper.toEntities(dtos)))
        }

    override fun delete(id: ID): AsyncResult<Unit> =
        attempt("Unable to delete entity $id") {
            repository.delete(daoDelegate.buildEntityId(id))
            Unit
        }

    override fun delete(ids: List<ID>): AsyncResult<Unit> =
        attempt("Unable to delete entities") {
            repository.delete(ids.map { daoDelegate.buildEntityId(it) })
            Unit
        }
}
