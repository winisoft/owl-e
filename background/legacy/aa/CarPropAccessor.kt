package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.db.library.car.CarPropRepository
import com.wini.owle.air.domain.car.CarProp
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

    fun getById(id: Long): Flow<CarProp?> {
        return repository.selectById(id).map { result -> result?.mapToModel()  }
    }

    override fun CarProp.mapToEntity(): CarPropEntity {
        return CarPropEntity(id, name, propertySource)
    }

}