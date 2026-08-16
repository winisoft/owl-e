package com.wini.owle.entity.environs.pkg

import com.wini.owle.entity.environs.EnvMarkerExpressions
import com.wini.owle.entity.environs.PackageDistRequirements
import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * One `Requires-Dist` line, parsed: which package requires which other package, under what
 * version constraint, gated by which environment markers.
 */
class PackageDistRequirementEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<PackageDistRequirementEntity>(PackageDistRequirements)

    var dependentDistribution by DistributionEntity referencedOn PackageDistRequirements.dependentDist
    var dependentVersion by VersionEntity referencedOn PackageDistRequirements.dependentVersion

    var requiredDistribution by DistributionEntity referencedOn PackageDistRequirements.requiredDist
    var requiredVersion by VersionEntity optionalReferencedOn PackageDistRequirements.requiredVersion

    /**
     * The extra being pulled in, for requirements written as `dist[extra]`.
     */
    var requiredExtraName by PackageDistRequirements.requiredExtraName

    var operator by PackageDistRequirements.operator
    var wildcardSegment by PackageDistRequirements.wildcardSegment

    /**
     * The original unparsed requirement string, kept for provenance.
     */
    var raw by PackageDistRequirements.raw

    /**
     * PEP 508 marker expressions gating this requirement. Roots have no parent.
     */
    val markerExpressions: SizedIterable<EnvMarkerExpressionEntity>
            by EnvMarkerExpressionEntity referrersOn EnvMarkerExpressions.requirement
}
