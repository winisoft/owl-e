package com.wini.owle.dto.environs.version

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/*
~Operator['<', '<=', '!=', '==', '>=', '>', '~=', '===']
~Release['FINAL', 'A', "B', "RC']
~Wildcard['MAJOR', 'MINOR', 'MICRO', 'NONE']
~MarkerClause['AND', 'OR", 'EXEC_ORDER']


- Distribution                             : a package name
  . name: String
  (. packages: List<Package>)            : has many packages : 1 --- * Package
- Package                                   : specific version of a distribution
  . versionId
  . distributionId                            : belongs to one Distribution
  (. requirements : List<Requirement)        : has many Requirements : 1 --- * Requirement
- Requirement                                       : a package's dependency
  . dependentPkgId (Package)                       : belongs to one Package
  . reqDistId (Distribution)                         : references one Distribution : * --- 1 Distribution
  (. versionSpecifiers: List<VersionSpecifier>)  : has many VersionSpecifiers  : 1 --- * VersionSpecifier
  . markerExprId: Int? (MarkerExpression?)                            : has 0 or 1 MarkerExpression : 0 --- 1 MarkerExpression
- MarkerExpression                                : logical condition of a requirement
  . requirementId: Int [Requirement]                               : belongs to one Requirement
  (. markerClauses: List<MarkerClause>)                              : has 1 or more MarkerClause
  (. logicalCombinators?: List<LogicalCombinator>?)      : has 0 or more (e.g. 'and', 'or'

- MarkerClause          : atomic comparison within a MarkerExpression
- LogicalCombinators
 */

@ModelDefinition
@ApiEndpointDefinition("api.environs.version.versionApi", ["environs"], "/version")
@Serializable
data class Version(
    override val id: ULong,
    val epoch: ULong,
    val major: ULong,
    val minor: ULong,
    val micro: ULong,
    val phase: PrereleasePhase,
    val phaseNumber: ULong?,
    val postNumber: ULong?,
    val devNumber: ULong?,
    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Version.LOCAL_MAX_LENGTH)
    val local: String?,
    val hasOverflow: Boolean,
    val versionOverflows: List<VersionOverflow>
): ULongDto {

    override val owleId: String get() = "epoch={${epoch}},major=${major},minor=${minor},micro=${micro},phase=${phase.name},phaseNumber${phaseNumber},postNumber=${postNumber},devNumber=${devNumber},local=${local},has_overflow=${hasOverflow}"

}