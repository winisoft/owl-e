package com.wini.owle.air.domain.environment

import arrow.core.Either
import com.wini.owle.air.db.environment.EnvironmentEntity
import com.wini.owle.air.db.environment.EnvironmentRepository
import com.wini.owle.air.db.environment.SelectAll
import com.wini.owle.air.db.environment.SelectById
import com.wini.owle.air.domain.action.SaveActions
import com.wini.owle.air.domain.action.SaveEnvironment
import com.wini.owle.air.domain.model.item.Environment
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.repo.DbSelectAllMapper
import com.wini.owle.air.domain.repo.DbSelectByIdMapper
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.LoError
import com.wini.owle.air.inlines.EnvironmentId
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single


class EnvironmentAccessor(
    private val repository: EnvironmentRepository
): DomainAccessor<Environment, EnvironmentEntity>(repository) {

    private val selectAllMapper: DbSelectAllMapper<SelectAll, Environment>
        get() = object: DbSelectAllMapper<SelectAll, Environment> {
            override fun SelectAll.mapSelectAllToModel(): Environment = Environment(
                id = id,
                name = name,
                description = description,
                isEnabled = isEnabled,
                isExpanded = isExpanded,
                isAddChildBtnVisible = false,
                isVisible = isVisible,
                isImplicitDisable = isDisabledImplicit,
                isHorizontalDisable = isHorizontalDisabled
            )
        }

    private val selectByIdMapper: DbSelectByIdMapper<SelectById, Environment?>
        get() = object: DbSelectByIdMapper<SelectById, Environment?> {
            override fun SelectById?.mapSelectByIdToModel(): Environment? {
                return this?.run {
                    Environment(
                        id = id,
                        name = name,
                        description = description,
                        isEnabled = isEnabled,
                        isExpanded = isExpanded,
                        isAddChildBtnVisible = false,
                        isVisible = isVisible,
                        isImplicitDisable = isDisabledImplicit,
                        isHorizontalDisable = isHorizontalDisabled
                    )
                }
            }
        }

    fun setIsEnabled(item: Environment): Flow<Either<LoError, Unit>> = repository.updateSetIsEnabledTrue(item.id)
        .map { result ->
            result.mapLeft { cause ->
                LoError.fromThrowable(cause)
            }
        }

    fun all(): Flow<List<LoItem>> = flowOf(repository.queries.selectAll().executeAsList()).map { list -> list.map {
            Environment(
                id = it.id,
                name = it.name,
                description = it.description,
                isEnabled = it.isEnabled,
                isExpanded = it.isExpanded,
                isAddChildBtnVisible = false,
                isVisible = it.isVisible,
                isImplicitDisable = it.isDisabledImplicit,
                isHorizontalDisable = it.isHorizontalDisabled
            )
        }
    }

    suspend fun save(saveEnvironment: SaveEnvironment): Flow<Either<LoError, Unit>> {
        return repository.update(saveEnvironment.entity).map { result ->
            result.mapLeft { cause ->
                LoError.fromThrowable(cause)
            }
        }
    }

    override fun Environment.mapToEntity(): EnvironmentEntity {
        return EnvironmentEntity(id, name, description, isEnabled, isExpanded, isHorizontalDisable)
    }

    fun getById(environmentId: EnvironmentId): Flow<Either<LoError, Environment>> = repository.selectById(environmentId)
        .map { result ->
            result.mapLeft { cause ->
                Either.Left(LoError.fromThrowable(cause))
            }.orNull()?.run {
                selectByIdMapper.run {
                    Either.Right(mapSelectByIdToModel()!!)
                }
            } ?: Either.Left(LoError.ExpectedDbValueNotFound as LoError)
        }

    fun getIdWhereEnabled(): Flow<EnvironmentId?> = repository.selectIdOfEnabled()

    fun getIsHorizontalDisabled(id: EnvironmentId): Flow<Boolean> = repository.selectIdOfEnabled().map {
        it == id
    }


    fun getNextId(): EnvironmentId = repository.queries.getMaxId().executeAsOne()
}