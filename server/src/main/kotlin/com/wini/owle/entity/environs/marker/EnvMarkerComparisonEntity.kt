package com.wini.owle.entity.environs.marker

import com.wini.owle.entity.environs.EnvMarkerComparisons
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * The leaf body of a COMPARATIVE marker expression — `python_version >= "3.9"` and friends.
 */
class EnvMarkerComparisonEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<EnvMarkerComparisonEntity>(EnvMarkerComparisons)

    var expression by EnvMarkerExpressionEntity referencedOn EnvMarkerComparisons.expression

    /**
     * The marker variable being tested, as written.
     */
    var variable by EnvMarkerComparisons.variable

    var operator by EnvMarkerComparisons.operator

    var value by EnvMarkerComparisons.value
}
