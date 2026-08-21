package com.wini.owle.dto.testing

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.dto.environs.version.enums.OverflowViolation
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.environs.enums.EnvMarkerOperator

/**
 * Canned [environs] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object EnvironsDtoFactory {

    fun envMarkerComparison(
        id: ULong = 1UL,
        expression: EnvMarkerExpression = envMarkerExpression(),
        variable: EnvMarkerVariable? = EnvMarkerVariable.SYS_PLATFORM,
        operator: EnvMarkerOperator = EnvMarkerOperator.EQ,
        value: String = "darwin"
    ): EnvMarkerComparison = EnvMarkerComparison(
        id = id,
        expression = expression,
        variable = variable,
        operator = operator,
        value = value
    )

    fun envMarkerExpression(
        id: ULong = 1UL,
        requirement: PackageDistRequirement = packageRequiredDist(),
        parent: EnvMarkerExpression? = null,
        mode: EnvMarkerExpressionMode = EnvMarkerExpressionMode.COMPARATIVE
    ) : EnvMarkerExpression = EnvMarkerExpression(
        id = id,
        parent = parent,
        mode = mode
    )

    fun distributionPackage(): DistributionPackage = DistributionPackage(
        distribution = distribution(),
        version = version(),
        versionString = "",
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        false,
        null,
        //emptyList()
    )

    fun packageRequiredDist(
        id: ULong = 1UL,
        dependentDistribution: Distribution = distribution(),
        dependentVersion: Version = version(),
        requiredDistribution: Distribution = distribution(),
        requiredVersion: Version? = version(),
        operator: VersionOperator = VersionOperator.EXACT,
        wildcardSegment: WildcardSegment = WildcardSegment.MINOR
    ) : PackageDistRequirement = PackageDistRequirement(
        id = id,
        dependentDistribution = dependentDistribution,
        dependentVersion = dependentVersion,
        requiredDistribution = requiredDistribution,
        requiredVersion = requiredVersion,
        operator = operator,
        wildcardSegment = wildcardSegment
    )

    fun distribution(
        name: String = "numpy"
    ): Distribution = Distribution(name)

    fun version(
        id: ULong = 10UL,
        epoch: ULong = 0UL,
        major: ULong = 1UL,
        minor: ULong = 0UL,
        micro: ULong = 0UL,
        phase: PrereleasePhase = PrereleasePhase.FINAL,
        phaseNumber: ULong? = null,
        postNumber: ULong? = null,
        devNumber: ULong? = null,
        local: String? = null,
        versionString: String = "1.0",
        hasOverflow: Boolean = false,
        overflowComponents: List<VersionOverflow> = emptyList()
    ): Version = Version(
        id = id,
        epoch = epoch,
        major = major,
        minor = minor,
        micro = micro,
        phase = phase,
        phaseNumber = phaseNumber,
        postNumber = postNumber,
        devNumber = devNumber,
        local = local,
        hasOverflow = hasOverflow,
        versionOverflows = overflowComponents
    )

    fun versionOverflow(
        id: ULong = 1UL,
        version: Version = version(),
        violation: OverflowViolation = OverflowViolation.PHASE_NUMBER,
        stringValue: String = ""
    ): VersionOverflow = VersionOverflow(
        id = id,
        version = version,
        violation = violation,
        stringValue = stringValue
    )

    fun pythonVersionSpecifier(
        pythonVersion: PythonVersion = pythonVersion(),
        operator: VersionOperator = VersionOperator.EXACT,
        wildcard: WildcardSegment = WildcardSegment.MINOR
    ): PythonVersionSpecifier = PythonVersionSpecifier(
        pythonVersion = pythonVersion,
        operator = operator,
        wildcard = wildcard
    )

    fun pythonVersion(
        id: ULong = 1UL,
        version: Version = version()
    ): PythonVersion = PythonVersion(
        id,
        version
    )
}
