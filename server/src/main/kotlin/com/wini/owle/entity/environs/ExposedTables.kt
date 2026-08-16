package com.wini.owle.entity.environs

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerLogicOp
import com.wini.owle.dto.environs.enums.EnvMarkerOperator
import com.wini.owle.entity.StringIdTable
import com.wini.owle.entity.environs.version.OverflowViolation
import com.wini.owle.entity.environs.version.PrereleasePhase
import com.wini.owle.entity.environs.version.VersionOperator
import com.wini.owle.entity.environs.version.WildcardSegment
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.or


object Distributions : StringIdTable("distributions")


object DistributionPackages : CompositeIdTable("packages") {
    val version = reference("version_id", Versions)
    val distribution = reference("distribution", Distributions)
    val versionString = varchar("version_str", 255)
    val summary = varchar("summary", 4096).nullable()
    val keywords = text("keywords").nullable()
    val description = text("description").nullable()
    val descriptionContentType = varchar("description_content_type", 1024).nullable()
    val license = varchar("license", 2048).nullable()
    val homePage = varchar("home_page", 255).nullable()
    val projectUrl = varchar("project_url", 255).nullable()
    val downloadUrl = varchar("download_url", 255).nullable()
    val docsUrl = varchar("docs_url", 255).nullable()
    val yanked = bool("yanked").nullable()
    val lastSerial = uinteger("last_serial").nullable()

    override val primaryKey = PrimaryKey(version, distribution)
}


object PackageExtras : CompositeIdTable("package_extras") {
    val distribution = reference("distribution", Distributions)
    val packageVersion = reference("package_id", Versions)
    val name = varchar("name", 255).entityId()
    val description = varchar("description", 4096).nullable()
    override val primaryKey = PrimaryKey(distribution, packageVersion, name)
}


object PackageDistRequirements : ULongIdTable("package_dist_requirements") {
    val dependentDist = reference("dependent_dist", Distributions)
    val dependentVersion = reference("dependent_package_id", Versions)
    val requiredDist = reference("required_dist", Distributions)
    val requiredVersion = reference("required_package_id", Versions).nullable()
    val requiredExtraName = varchar("required_extra_name", 255).nullable()
    val operator = enumerationByName("operator", 16, VersionOperator::class)
    val wildcardSegment = enumerationByName("wildcard_segment", 8, WildcardSegment::class)
    val raw = varchar("raw", 1024).nullable()
}


object PackagePythonRequirements : CompositeIdTable("package_python_requirements") {
    val distribution = reference("distribution", Distributions)
    val packageVersion = reference("package_id", Versions)
    val pythonVersion = reference("version_id", PythonVersions)
    val operator = enumerationByName("operator", 16, VersionOperator::class)
    val wildcard = enumerationByName("wildcard", 8, WildcardSegment::class).default(WildcardSegment.NONE)
    override val primaryKey = PrimaryKey(packageVersion, distribution)
}


/**
 * Maps 1:1 onto Versions.id — the `id` column is not its own auto-increment; it reuses the parent Versions PK.
 */
object PythonVersions : IdTable<ULong>("python_versions") {
    override val id: Column<EntityID<ULong>> = reference("id", Versions, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(id, name = "PK_python_versions_id")
}


object PythonVersionSpecifiers : CompositeIdTable("python_version_specifiers") {
    val pythonVersion = reference("version_id", PythonVersions)
    val operator = enumerationByName("operator", 16, VersionOperator::class).entityId()
    val wildcard = enumerationByName("wildcard", 8, WildcardSegment::class).entityId()
    override val primaryKey = PrimaryKey(pythonVersion, operator, wildcard)
}


object Versions : ULongIdTable("versions") {
    val epoch = ulong("epoch").default(0uL)
    val major = ulong("major")
    val minor = ulong("minor").default(0uL)
    val micro = ulong("micro").default(0uL)
    val phase = enumerationByName("phase", 16, PrereleasePhase::class).default(PrereleasePhase.FINAL)
    val phaseNumber = ulong("phase_number").nullable()
    val postNumber = ulong("post_number").nullable()
    val devNumber = ulong("dev_number").nullable()
    val local = varchar("local", 255).nullable()
    val hasOverflow = bool("has_overflow").default(false)

    init {
        uniqueIndex(
            "uniq_normal_version",
            epoch, major, minor, micro,
            phase, phaseNumber,
            postNumber, devNumber, local
        )

        index("idx_version_components", false, major, minor, micro)

        check("versions_chk_phase") {
            ((phase eq PrereleasePhase.FINAL) and phaseNumber.isNull()) or
                ((phase neq PrereleasePhase.FINAL) and phaseNumber.isNotNull())
        }

        check("versions_chk_post") {
            (postNumber.isNotNull() and (phase eq PrereleasePhase.FINAL)) or postNumber.isNull()
        }

        check("versions_chk_dev") {
            (devNumber.isNotNull() and (phase eq PrereleasePhase.FINAL) and postNumber.isNull()) or devNumber.isNull()
        }
    }
}


object VersionOverflows : ULongIdTable("version_overflows") {
    val version = reference("version_id", Versions, onDelete = ReferenceOption.CASCADE)
    val violation = enumerationByName("violation", 32, OverflowViolation::class)
    val stringValue = varchar("string_value", 255)

    init {
        uniqueIndex("unique_component", version, violation)
    }
}


object EnvMarkerExpressions : ULongIdTable("env_marker_expressions") {
    val requirement = reference("requirement_id", PackageDistRequirements)
    val parent = reference("parent_id", EnvMarkerExpressions).nullable()
    val mode = enumerationByName("mode", 16, EnvMarkerExpressionMode::class)
}


object EnvMarkerLogicals : ULongIdTable("env_marker_logicals") {
    val expression = reference("expression_id", EnvMarkerExpressions, onDelete = ReferenceOption.CASCADE)
    val operator = enumerationByName("operator", 4, EnvMarkerLogicOp::class)
    val leftChild = reference("left_child_id", EnvMarkerExpressions)
    val rightChild = reference("right_child_id", EnvMarkerExpressions)
}


object EnvMarkerComparisons : ULongIdTable("env_marker_comparisons") {
    val expression = reference("expression_id", EnvMarkerExpressions, onDelete = ReferenceOption.CASCADE)
    val variable = varchar("variable", 255).nullable()
    val operator = enumerationByName("operator", 18, EnvMarkerOperator::class)
    val value = text("value")
}
