package com.wini.owle.air.domain.repo

import arrow.core.Either
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.domain.action.SaveActions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Base class for all repositories in the domain layer, the single point of access to the data layer for
 * any other module.
 *
 * For every data type in the data model, there is a pre-defined call for CRUD operations
 * (add/save/remove) that simply calls into the pre-defined [LoRepository] functions. Accordingly,
 * all implementations are required to define the [ModelToEntityMapper] function to facilitate that simple
 * hand-off.
 *
 * However, the sqldelight database generates types after interpreting the defined SQL procedures, that
 * reflect the specific fields the procedure queries for. Alternatively, if it uses the wildcard selector, it
 * relies on the [Entity] model. We can't know whether a type will have any fn with 'entity' type
 * results, or some other type of result, likely a 'SelectAll' or 'SelectById' model to map, so mappers
 * for those ad-hoc types must be provided.
 */
abstract class DomainAccessor<Model: Any, Entity: Any>(
    /**
     * The database-accessing repository in the data layer responsible for the corresponding entity representation
     * of this type of model.
     */
    private val dataLayer: LoRepository<Entity>
): ModelToEntityMapper<Model, Entity> {

    /**
     * Adds a new object into the data model by calling the data layer [LoRepository] to insert it into the database.
     * @param model a generically typed data object
     * @return a flow of either A.) an [LoError] that results from receiving a [Throwable] result in the data layer
     * function and interpreting it here or B.) a Unit signifying the data element was successfully added.
     */
    suspend fun add(model: Model): Flow<Either<LoError, Unit>> = model.mapToEntity().run {
        dataLayer.insert(this)
    }.map { result ->
        result.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    /**
     * Persists an existing object in data model by calling the data layer [LoRepository] to update it in the database.
     * @param model a generically typed data object
     * @return a flow of either A.) an [LoError] that results from receiving a [Throwable] result in the data layer
     * function and interpreting it here or B.) a Unit signifying the data element was successfully updated.
     */
    suspend fun save(model: Model): Flow<Either<LoError, Unit>> = model.mapToEntity().run {
        dataLayer.update(this)
    }.map { result ->
        result.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    suspend fun <T: Entity> save(saveAction: SaveActions<T>): Flow<Either<LoError, Unit>> = saveAction.entity.run {
        dataLayer.update(this)
    }.map { result ->
        result.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

    /**
     * Removes an object from the data model by calling the data layer [LoRepository] to delete it from the database.
     * @param model a generic data object
     * @return a flow of either A.) an [LoError] that results from receiving a [Throwable] result in the data layer
     * function and interpreting it here or B.) a Unit signifying the data element was successfully removed.
     */
    suspend fun remove(model: Model): Flow<Either<LoError, Unit>> = model.mapToEntity().run {
        dataLayer.delete(this)
    }.map { result ->
        result.mapLeft { cause ->
            LoError.fromThrowable(cause)
        }
    }

}