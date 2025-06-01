package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class PhaseRelationEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<PhaseRelationEntity>(PhaseRelations)

    var parent by PhaseEntity referencedOn PhaseRelations.parent

    var child by PhaseEntity referencedOn PhaseRelations.child

}