package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.domain.car.CarProp

class CarPropEntityToModelMapper: EntityToModelMapper<CarPropEntity, CarProp> {

    override fun CarPropEntity.mapToModel(): CarProp {
        return CarProp(id, name, propertySource)
    }
}