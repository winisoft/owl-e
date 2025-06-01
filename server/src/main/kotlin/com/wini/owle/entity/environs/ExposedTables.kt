package com.wini.owle.entity.environs

import com.wini.owle.dto.environs.enums.*
import com.wini.owle.entity.StringIdTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable



object EnvMarkerExpressions : ULongIdTable("env_marker_expressions") {
    val requirement = reference("requirement_id", PackageDistRequirements)
    val parent = optReference("parent_id", EnvMarkerExpressions)
    val mode = enumerationByName("mode", 16, EnvMarkerExpressionMode::class)
}


object EnvMarkerLogicals : ULongIdTable("env_marker_logicals") {
    val expression = reference("expression_id", EnvMarkerExpressions)
    val operator = enumerationByName("operator", 4, EnvMarkerLogicOp::class)
    val leftChild = reference("left_child_id", EnvMarkerExpressions)
    val rightChild = reference("right_child_id", EnvMarkerExpressions)
}


object EnvMarkerComparisons: ULongIdTable("env_marker_comparisons") {
    val expression = reference("expression_id", EnvMarkerExpressions, onDelete = ReferenceOption.CASCADE)
    val operator = enumerationByName("operator", 18, EnvMarkerOperator::class)
    val variable = enumerationByName("variable", 30, EnvMarkerVariable::class).nullable()
    val value = text("value")
}


object Distributions : StringIdTable("distributions")


object DistributionPackages: CompositeIdTable("packages") {
    val distribution = reference("distribution", Distributions, onDelete = ReferenceOption.CASCADE)
    val version = reference("version_id", Versions)
    val versionString = varchar("version_str", 255)
    val summary = varchar("summary", 4096).nullable()
    val keywords = text("keywords").nullable()
    val description = text("description").nullable()
    val descriptionContentType = varchar("description_content_type", 1024).nullable()
    val license = varchar("license", 255).nullable()
    val homePage = varchar("home_page", 255).nullable()
    val projectUrl = varchar("project_url", 255).nullable()
    val downloadUrl = varchar("download_url", 255).nullable()
    val docsUrl = varchar("docs_url", 255).nullable()
    val yanked = bool("yanked")
    val lastSerial = uinteger("last_serial").nullable()

    override val primaryKey = PrimaryKey(version, distribution)
}


object PackageExtras : CompositeIdTable("package_extras") {
    val distribution = reference("distribution", Distributions, onDelete = ReferenceOption.CASCADE)
    val `package` = reference("package_id", DistributionPackages, onDelete = ReferenceOption.CASCADE)
    val name = varchar("name", 255).entityId()
    val description = varchar("description", 4096)
    override val primaryKey = PrimaryKey(name, distribution, `package`)
}


object PackageDistRequirements : ULongIdTable("package_dist_requirements") {
    val dependentDist = reference("dependent_dist", Distributions, onDelete = ReferenceOption.CASCADE)
    val dependentVersion = reference("dependent_package_id", Versions)
    val requiredDist = reference("required_dist", Distributions, onDelete = ReferenceOption.CASCADE)
    val requiredVersion = optReference("required_package_id", Versions)
    val operator = enumerationByName("operator", 10, VersionOperator::class)
    val wildcardSegment = enumerationByName("wildcard_segment", 5, WildcardSegment::class)
    init {
        uniqueIndex(dependentDist, dependentVersion, requiredDist, `operator`, wildcardSegment)
    }
}


object PackagePythonRequirements : CompositeIdTable("package_python_requirements") {
    val distribution = reference("distribution", Distributions, onDelete = ReferenceOption.CASCADE)
    val version = reference("version_id", PythonVersions)
    val operator = enumerationByName("operator", 12, VersionOperator::class)
    val wildcard = enumerationByName("wildcard", 10, WildcardSegment::class)
}

/**
 * Maps 1:1 onto Versions.id.
 * The `id` column here is *not* auto­incremented—it reuses the parent Versions PK.
 */
object PythonVersions : IdTable<ULong>("python_versions") {
    /**
     * Primary key *and* foreign key to Versions.id
     */
    override val id: Column<EntityID<ULong>> =
        reference(
            name = "id",
            foreign = Versions,
            onDelete = ReferenceOption.CASCADE
        )

    /**
     * Whether this Python version is still supported.
     */
    val isSupported: Column<Boolean> = bool("is_supported").default(false)

    /**
     * Ensure Exposed knows this is the table’s sole PK.
     */
    override val primaryKey = PrimaryKey(id, name = "PK_python_versions_id")
}


object PythonVersionSpecifiers : ULongIdTable("python_version_specifiers") {
    val pythonVersion = reference("version_id", PythonVersions)
    val operator = enumerationByName("operator", 16, VersionOperator::class)
    val wildcard = enumerationByName("wildcard", 16, WildcardSegment::class).default(WildcardSegment.NONE)
}

object Versions : ULongIdTable("versions") {
    val epoch = ulong("epoch")
    val major = ulong("major")
    val minor = ulong("minor")
    val micro = ulong("micro")
    val phase = enumerationByName("phase", 16, PrereleasePhase::class).default(PrereleasePhase.FINAL)
    val phaseNumber = ulong("phase_number").nullable()
    val postNumber = ulong("post_number").nullable()
    val devNumber = ulong("dev_number").nullable()
    val local = varchar("local", 255).nullable()
    val hasOverflow = bool("has_overflow").default(false)

    init {
        uniqueIndex(
            "unique_normal_version",
            major, minor, micro,
            phase, phaseNumber,
            postNumber, devNumber, local,
            hasOverflow
        )

        index("idx_version_components", false, major, minor, micro)
    }
}


object VersionOverflows : ULongIdTable("version_overflows") {
    val version = reference("version_id", Versions)
    val violation = enumerationByName("violation", 32, OverflowViolation::class)
    val stringValue = varchar("string_value", 255)

    init {
        uniqueIndex("unique_component", version, violation)
    }
}

