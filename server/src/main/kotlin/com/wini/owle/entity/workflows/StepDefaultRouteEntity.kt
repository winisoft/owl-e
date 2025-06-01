package com.wini.owle.entity.workflows


import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class StepDefaultRouteEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<StepDefaultRouteEntity>(StepDefaultRoutes)

    var refStep by StepEntity referencedOn StepDefaultRoutes.refStep

    var defaultNext by StepEntity referencedOn StepDefaultRoutes.defaultNext

}