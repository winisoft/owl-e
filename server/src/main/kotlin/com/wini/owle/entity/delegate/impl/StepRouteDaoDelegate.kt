package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.workflows.StepDefaultRoute
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.workflows.StepDefaultRouteEntity

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


object StepRouteDaoDelegate: DaoDelegate<CompositeID, StepDefaultRoute, StepDefaultRouteEntity> {
    override val columnsByRequestParam: Map<String, Column<out Any>>
        get() = TODO("Not yet implemented")

    override fun extractId(dto: StepDefaultRoute): CompositeID {
        TODO("Not yet implemented")
    }

    override fun buildEntityId(id: CompositeID): EntityID<CompositeID> {
        TODO("Not yet implemented")
    }

    override fun findOrCreate(
        dto: StepDefaultRoute,
        applyProps: StepDefaultRouteEntity.() -> Unit
    ): StepDefaultRouteEntity {
        TODO("Not yet implemented")
    }

    override fun buildId(dto: StepDefaultRoute): EntityID<CompositeID> {
        TODO("Not yet implemented")
    }
}