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
    /** Raw version half of the dependent package's key. */
    var dependentVersionId by PackageDistRequirements.dependentVersion

    /**
     * Version of the package that declares the dependency. Backed by [dependentVersionId] rather
     * than a foreign key: the database constrains (dependent_dist, dependent_package_id) into
     * `packages`, not this column alone.
     */
    var dependentVersion: VersionEntity
        get() = VersionEntity[dependentVersionId]
        set(value) { dependentVersionId = value.id.value }

    var requiredDistribution by DistributionEntity referencedOn PackageDistRequirements.requiredDist
    /** Raw version half of the required package's key; null means "any version". */
    var requiredVersionId by PackageDistRequirements.requiredVersion

    /**
     * Version of the required package, or null when any version satisfies the dependency.
     * Backed by [requiredVersionId] for the same reason as [dependentVersion].
     */
    var requiredVersion: VersionEntity?
        get() = requiredVersionId?.let { VersionEntity[it] }
        set(value) { requiredVersionId = value?.id?.value }

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
