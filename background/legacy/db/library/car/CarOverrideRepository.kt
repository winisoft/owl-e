package com.wini.owle.air.db.library.car

import arrow.core.Either
import com.wini.owle.air.db.api.SelectApiOverrideBreakdown
import com.wini.owle.air.db.car.*
import com.wini.owle.air.db.error.UnknownDbExecuteException
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.*
import kotlinx.coroutines.flow.*


class CarOverrideRepository(
    override val queries: CarOverrideEntityQueries
): com.wini.owle.air.db.repository.LoRepository<CarOverrideEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<SelectAll>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: CarOverrideId): Flow<app.cash.sqldelight.Query<SelectById>> = flowOf(queries.selectById(id))

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectById(id: CarOverrideId): Flow<SelectById?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    private fun getNextId(): CarOverrideId = queries.getMaxId().executeAsOne().run { CarOverrideId(value + 1) }
    fun getIsFullyEnabledAndCarPropId(id: CarPropId): Flow<Either<Throwable?, SelectEnabledForProp>> = flowOf(queries
        .selectEnabledForProp(id)
        .executeAsOneOrNull().let {
            it?.run {
                Either.Right(this)
            } ?: Either.Left(Throwable().fillInStackTrace())
        })

    override val insertEntityExecution: (CarOverrideEntity) -> Unit = {
        it.run { queries.insert(getNextId(), carPropId, overrideName, environmentId, overrideValue, isEnabled) }
    }

    override val updateEntityExecution: (CarOverrideEntity) -> Unit = {
        it.run { queries.update(carPropId, overrideName, environmentId, overrideValue, isEnabled, id) }
    }

    override val deleteEntityExecution: (CarOverrideEntity) -> Unit = {
        queries.delete(it.id)
    }

    fun updateSetIsEnabledTrue(
        id: CarOverrideId,
        carPropId: CarPropId,
        envId: EnvironmentId
    ): Flow<Either<Throwable?, Unit>> = runCatching { queries.updateCarOverrideSetIsEnabledTrue(id, id, carPropId, envId) }.run {
        exceptionOrNull()?.run {
            flowOf(Either.Left(this))
        } ?: getOrNull()?.run {
            flowOf(Either.Right(this))
        } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
    }

    fun selectWhereEnvironmentId(envId: EnvironmentId): Flow<List<SelectWhereEnvironmentId>> = flowOf(queries
        .selectWhereEnvironmentId(envId)
        .executeAsList())

    fun getCarOverrideBreakdown(envId: EnvironmentId): Flow<SelectCarOverrideBreakdown> {
        return flowOf(queries.selectCarOverrideBreakdown(envId).executeAsOne())
    }
}