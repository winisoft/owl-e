package com.wini.owle.entity.environs.marker

import com.wini.owle.entity.environs.pkg.PackageDistRequirementEntity
import com.wini.owle.entity.environs.pkg.PackageDistRequirements
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class EnvMarkerExpressionEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<EnvMarkerExpressionEntity>(EnvMarkerExpressions)
    var requirement by PackageDistRequirementEntity referencedOn EnvMarkerExpressions.requirement
    var parent by EnvMarkerExpressionEntity optionalReferencedOn EnvMarkerExpressions.parent
    var mode by EnvMarkerExpressions.mode


}
