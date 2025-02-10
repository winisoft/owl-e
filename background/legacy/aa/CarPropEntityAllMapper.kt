package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.domain.car.CarProp

class CarPropEntityAllMapper: ModelToEntityMapper<CarProp, CarPropEntity> {
    override fun CarProp.mapToEntity(): CarPropEntity {
        return CarPropEntity(id, name, propertySource)
    }

}