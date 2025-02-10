package com.wini.owle.air.domain.car

import com.wini.owle.air.db.library.car.PropertySource

import com.wini.owle.air.inlines.CarPropId
import com.wini.owle.air.inlines.Name

data class CarProp(
    val id: CarPropId,
    val name: Name,
    val propertySource: PropertySource
)