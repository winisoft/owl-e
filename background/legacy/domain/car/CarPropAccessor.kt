package com.wini.owle.air.domain.car

import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.db.library.car.CarPropRepository
import com.wini.owle.air.domain.repo.DbSelectByIdMapper
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.repo.EntityToModelMapper
import com.wini.owle.air.domain.repo.ModelToEntityMapper
import com.wini.owle.air.inlines.CarPropId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CarPropAccessor(
    private val repository: CarPropRepository
): DomainAccessor<CarProp, CarPropEntity>(repository),
    ModelToEntityMapper<CarProp, CarPropEntity> by CarPropEntityAllMapper(),
    EntityToModelMapper<CarPropEntity, CarProp> by CarPropEntityToModelMapper()
{
    
    fun getAll(): Flow<List<CarProp>> {
        return repository.selectAll().mapToModel()
    }

    fun getById(id: CarPropId): Flow<CarProp?> {
        return repository.selectById(id).map { result -> result?.mapToModel()  }
    }

    override fun CarProp.mapToEntity(): CarPropEntity {
        return CarPropEntity(id, name, propertySource)
    }

}