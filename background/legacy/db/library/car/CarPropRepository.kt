package com.wini.owle.air.db.library.car

import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.db.car.CarPropEntityQueries
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.CarPropId
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class CarPropRepository(
    override val queries: CarPropEntityQueries
): com.wini.owle.air.db.repository.LoRepository<CarPropEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<CarPropEntity>> = flowOf(queries.selectAll())

    fun selectAll(): Flow<List<CarPropEntity>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectByIdQuery(id: CarPropId): Flow<app.cash.sqldelight.Query<CarPropEntity>> = flowOf(queries.selectById(id))

    fun selectById(id: CarPropId): Flow<CarPropEntity?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    override val insertEntityExecution: (CarPropEntity) -> Unit = {
        it.run { queries.insert(id, name, propertySource) }
    }

    override val updateEntityExecution: (CarPropEntity) -> Unit = {
        it.run { queries.update(name, propertySource, id) }
    }

    override val deleteEntityExecution: (CarPropEntity) -> Unit = {
        queries.delete(it.id)
    }

}