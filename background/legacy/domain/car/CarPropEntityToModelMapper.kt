package com.wini.owle.air.domain.car

import com.wini.owle.air.db.car.CarPropEntity
import com.wini.owle.air.domain.repo.DbSelectAllMapper
import com.wini.owle.air.domain.repo.DbSelectByIdMapper
import com.wini.owle.air.domain.repo.EntityToModelMapper

class CarPropEntityToModelMapper: EntityToModelMapper<CarPropEntity, CarProp> {

    override fun CarPropEntity.mapToModel(): CarProp {
        return CarProp(id, name, propertySource)
    }
}