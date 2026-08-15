package com.wini.owle.entity.environs.marker

import com.wini.owle.entity.environs.EnvMarkerComparisons
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class EnvMarkerComparisonEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<EnvMarkerComparisonEntity>(EnvMarkerComparisons)

    var expression by EnvMarkerExpressionEntity referencedOn EnvMarkerComparisons.expression
    var variable by EnvMarkerComparisons.variable
    var operator by EnvMarkerComparisons.operator
    var value by EnvMarkerComparisons.value

}
