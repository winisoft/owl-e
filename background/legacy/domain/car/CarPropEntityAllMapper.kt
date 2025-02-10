package com.wini.owle.air.domain.car

import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.domain.repo.ModelToEntityMapper

class CarPropEntityAllMapper: ModelToEntityMapper<CarProp, CarPropEntity> {
    override fun CarProp.mapToEntity(): CarPropEntity {
        return CarPropEntity(id, name, propertySource)
    }

}