package com.wini.owle.entity.environs.marker

import com.wini.owle.entity.environs.EnvMarkerComparisons
import com.wini.owle.entity.environs.EnvMarkerExpressions
import com.wini.owle.entity.environs.EnvMarkerLogicals
import com.wini.owle.entity.environs.PackageDistRequirements
import com.wini.owle.entity.environs.pkg.PackageDistRequirementEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A node in a PEP 508 marker expression tree. [mode] says which shape it takes: a leaf
 * comparison ([comparison]) or an AND/OR of two children ([logical]).
 */
class EnvMarkerExpressionEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<EnvMarkerExpressionEntity>(EnvMarkerExpressions)

    var requirement by PackageDistRequirementEntity referencedOn EnvMarkerExpressions.requirement

    var parent by EnvMarkerExpressionEntity optionalReferencedOn EnvMarkerExpressions.parent

    var mode by EnvMarkerExpressions.mode

    /**
     * Child expressions nested beneath this one.
     */
    val children: SizedIterable<EnvMarkerExpressionEntity>
            by EnvMarkerExpressionEntity optionalReferrersOn EnvMarkerExpressions.parent

    /**
     * The leaf comparison, when [mode] is COMPARATIVE.
     */
    val comparison: EnvMarkerComparisonEntity?
            by EnvMarkerComparisonEntity optionalBackReferencedOn EnvMarkerComparisons.expression

    /**
     * The AND/OR node, when [mode] is LOGICAL.
     */
    val logical: EnvMarkerLogicalEntity?
            by EnvMarkerLogicalEntity optionalBackReferencedOn EnvMarkerLogicals.expression
}
