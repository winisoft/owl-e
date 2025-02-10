package com.wini.owle.air.domain.aa.environment

import arrow.core.Either
import com.wini.owle.air.db.environment.EnvironmentEntity
import com.wini.owle.air.db.environment.EnvironmentRepository
import com.wini.owle.air.db.environment.SelectAll
import com.wini.owle.air.db.environment.SelectById
import com.wini.owle.air.domain.action.SaveEnvironment
import com.wini.owle.air.domain.model.item.Environment
import com.wini.owle.air.domain.model.item.LoItem
import com.wini.owle.air.domain.aa.DbSelectAllMapper
import com.wini.owle.air.domain.aa.DbSelectByIdMapper
import com.wini.owle.air.domain.aa.DomainAccessor
import com.wini.owle.air.domain.aa.LoError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


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

    fun setBoolean(item: Environment): Flow<Either<LoError, Unit>> = repository.updateSetBooleanTrue(item.id)
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

    fun getById(Long: Long): Flow<Either<LoError, Environment>> = repository.selectById(Long)
        .map { result ->
            result.mapLeft { cause ->
                Either.Left(LoError.fromThrowable(cause))
            }.orNull()?.run {
                selectByIdMapper.run {
                    Either.Right(mapSelectByIdToModel()!!)
                }
            } ?: Either.Left(LoError.ExpectedDbValueNotFound as LoError)
        }

    fun getIdWhereEnabled(): Flow<Long?> = repository.selectIdOfEnabled()

    fun getIsHorizontalDisabled(id: Long): Flow<Boolean> = repository.selectIdOfEnabled().map {
        it == id
    }


    fun getNextId(): Long = repository.queries.getMaxId().executeAsOne()
}