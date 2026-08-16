package com.wini.owle.entity.environs.marker

import com.wini.owle.entity.environs.EnvMarkerLogicals
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * The AND/OR body of a LOGICAL marker expression.
 */
class EnvMarkerLogicalEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<EnvMarkerLogicalEntity>(EnvMarkerLogicals)

    var expression by EnvMarkerExpressionEntity referencedOn EnvMarkerLogicals.expression

    var operator by EnvMarkerLogicals.operator

    var left by EnvMarkerExpressionEntity referencedOn EnvMarkerLogicals.leftChild

    var right by EnvMarkerExpressionEntity referencedOn EnvMarkerLogicals.rightChild
}
