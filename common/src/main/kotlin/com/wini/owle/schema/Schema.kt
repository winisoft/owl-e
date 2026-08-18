package com.wini.owle.schema

import kotlinx.datetime.Instant

/**
 * Single source of truth for field capacities, sourced directly from
 * `INFORMATION_SCHEMA.COLUMNS` against `owle_dev` (2026-08-16). Every `*_MAX_LENGTH` /
 * `*_LENGTH` constant below names the actual `CHARACTER_MAXIMUM_LENGTH` of its DB column —
 * these are not estimates. Numeric columns (bigint/int/tinyint) need no constant here: Kotlin's
 * own Int/Long/UInt/ULong types already enforce their range at parse time. DECIMAL columns are
 * the one numeric exception — Kotlin's Double doesn't enforce fixed scale, so precision/scale
 * constants are recorded where a DECIMAL column exists.
 *
 * TEXT columns (MySQL cap 65,535) are not represented here unless a DTO deliberately imposes a
 * *tighter* app-level cap — that's a product decision, not a capacity fact, and stays local to
 * the DTO that makes it.
 */
object Schema {

    object Utils {
        val instant = Instant.parse("2024-12-20T10:00:00Z")
    }

    object SizeLimits {
        const val LEN_4 = 4
        const val LEN_7 = 7
        const val LEN_20 = 20
        const val LEN_64 = 64
        const val LEN_127 = 127
        const val LEN_128 = 128
        const val LEN_255 = 255
        const val LEN_512 = 512
        const val LEN_1024 = 1024
        const val LEN_2048 = 2048
        const val LEN_4096 = 4096
        const val LEN_8192 = 8192
        const val LEN_16384 = 16384
        const val LEN_16000 = 16000
        const val LEN_64000 = 64_000
        const val LEN_5012 = 5012
    }

    /**
     * Bounds for the canvas coordinates carried by `steps`, `groupers` and `multiflow_workflows`.
     *
     * The database is not what constrains these — `DOUBLE` holds ~1.8e308. The renderer is:
     * Compose/Skia works in 32-bit floats, whose 24-bit mantissa represents integers exactly only
     * up to 2^24 (16,777,216). Past that, precision degrades with distance from the origin:
     *
     *   |x| ~ 1e6   ULP = 1/16 px   imperceptible
     *   |x| ~ 1e7   ULP = 1 px      subpixel positioning gone
     *   |x| ~ 1e8   ULP = 8 px      nodes visibly snap to a grid
     *
     * The failure is not clipping, it is drift. Screen position is computed as `world - camera`,
     * so once both operands are large the subtraction loses its low bits and a node jitters while
     * the user pans — which reads as a rendering bug, not as a limit anyone chose.
     *
     * [MAX] therefore sits an order of magnitude below where that starts, giving a plane 2,000,000
     * units across (roughly two thousand screen-widths) with 1/16 px fidelity at the very edge.
     * A graph that genuinely outgrows this wants nesting, which is what a Grouper is for — not a
     * larger plane, which would only move the drift somewhere the user notices it less.
     */
    object Canvas {
        const val MIN = -1_000_000.0
        const val MAX = 1_000_000.0
    }

    object DataPoint {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
        // preset_value is TEXT (65,535) in the DB; any cap here is an app-level policy choice.
    }

    object Grouper {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_1024
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
        const val COLOR_LENGTH = SizeLimits.LEN_7 // "#RRGGBB"
    }

    /** Retired — steps.parent_grouper_id / groupers replaced phases entirely; no `phases` table remains. */
    object Phase {
    }

    object Step {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
        const val COLOR_LENGTH = SizeLimits.LEN_7
    }

    object Tool {
        // name and body are TEXT in the DB (no CHARACTER_MAXIMUM_LENGTH fact to source from);
        // any cap a DTO applies to them is an app-level policy choice, not recorded here.
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    object Workflow {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    object WorkflowFolder {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    /** Maps the `metadata` table (sharing/licensing terms for a distributed workflow). */
    object WorkflowMetadata {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val VERSION_MAX_LENGTH = SizeLimits.LEN_20
        const val DISTRIBUTION_VERSION_MAX_LENGTH = SizeLimits.LEN_255
        // copyright_information / contact_information / third_party_licenses are TEXT (65,535).
    }

    object WorkflowRequirements {
        const val REQUIRED_EXTRA_NAME_MAX_LENGTH = SizeLimits.LEN_255
    }

    object Multiflow {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    object MultiflowWorkflow {
        const val COLOR_LENGTH = SizeLimits.LEN_7
    }

    object User {
        const val EMAIL_MAX_LENGTH = SizeLimits.LEN_255
        const val PASSWORD_MAX_LENGTH = SizeLimits.LEN_255
        const val RESET_TOKEN_MAX_LENGTH = SizeLimits.LEN_255
        const val OAUTH_ID_MAX_LENGTH = SizeLimits.LEN_255
        const val API_KEY_MAX_LENGTH = SizeLimits.LEN_255
        const val FIRST_NAME_MAX_LENGTH = SizeLimits.LEN_128
    }

    object PaymentMethod {
        const val STRIPE_ID_MAX_LENGTH = SizeLimits.LEN_255
        const val LAST_FOUR_LENGTH = SizeLimits.LEN_4
    }

    object TypeVarVariance {
    }

    object TypeVarConstraint {
    }

    object TypeVarBound {
    }

    object TypeUnionMember {
    }

    object TypeSchemaRef {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_4096
    }

    /** No `type_parameters` table exists in the current schema — `type_generic_params` is its replacement. */
    object TypeParameter {
    }

    object TypeGenericParam {
    }

    object TypeInstantiation {
    }

    object TypeInstantiationArg {
    }

    object TypeAlias {
    }

    object CallableOwner {
    }

    object TypeNode {
        const val NAMESPACE_MAX_LENGTH = SizeLimits.LEN_1024
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val ALIAS_MAX_LENGTH = SizeLimits.LEN_255
        const val DEDUP_HASH_LENGTH = SizeLimits.LEN_64 // sha-256 hex digest, fixed-length
    }

    /** Maps `type_node_relations`. */
    object ClassInheritance {
    }

    object CallableSignature {
        const val DOCSTRING_MAX_LENGTH = SizeLimits.LEN_8192
    }

    object CallableParameter {
        const val PARAM_NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DEFAULT_VALUE_TEXT_MAX_LENGTH = SizeLimits.LEN_1024
    }

    object TransformTargetType {
    }

    /** Maps `transform_occurence_args` (DB spelling — single 'r'). */
    object TransformOccurrenceArg {
        // value is TEXT (65,535).
    }

    object TransformOccurrence {
    }

    object TransformOccurrenceChain {
    }

    object TransformArg {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    object Transform {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_1024
        /**
         * DB is varchar(2046) — two short of the otherwise-consistent 2048 bucket used
         * elsewhere (packages.license). Almost certainly a typo at table-creation time
         * rather than a deliberate cap; flagged for confirmation, not silently "corrected"
         * to 2048 here since the DB is the source of truth this constant must not exceed.
         */
        const val EXAMPLES_MAX_LENGTH = 2046
    }

    object TagRelation {
    }

    object Tag {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    object WorkflowTag {
    }

    object WorkflowFolderTag {
    }

    object TransformTag {
    }

    object ToolTag {
    }

    object StepTag {
    }

    /** Retired — `tagged_phases` no longer exists; `tagged_groupers` (see [GrouperTag]) replaced it. */
    object PhaseTag {
    }

    object GrouperTag {
    }

    object PackageTag {
    }

    object DistributionTag {
    }

    object WorkflowJunction {
    }

    /** Retired — `tool_params` no longer exists; `callable_parameters` is the sole call-contract authority. */
    object ToolParam {
    }

    object Inquisition {
        const val NOTE_MAX_LENGTH = SizeLimits.LEN_8192
        // body is TEXT (65,535).
    }


    object InquisitionStepRedirect {
    }

    object InquisitionJunctionRedirect {
    }


    object ErrorWatchFn {
    }

    object ErrorWatch {
        const val ERROR_TYPE_MAX_LENGTH = SizeLimits.LEN_255
    }

    /** Retired — `provision_data_points` no longer exists. */
    object DataPointWriteLink {
    }

    object DataPointWrite {
    }

    object DataPointWriteArg {
    }

    /** One read table again, discriminated by owner; see `data_point_reads`. */
    object DataPointRead {
    }

    object DataPointInjection {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    /** Maps `test_results`. */
    object WorkflowTestResult {
    }

    object WorkflowRun {
    }

    object TestInput {
        // value is TEXT (65,535).
    }

    /** Maps `test_expected_outcomes`. */
    object TestExpectation {
        // expected_value is TEXT (65,535).
    }

    object TestCase {
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_8192
    }

    object BenchmarkReport {
        /** `execution_time DECIMAL(10,3)` — total digits / digits after the decimal point. */
        const val EXECUTION_TIME_PRECISION = 10
        const val EXECUTION_TIME_SCALE = 3
    }

    /** Maps `execution_logs`. */
    object StepRunLogLine {
        // message is TEXT (65,535); detail is JSON.
    }

    /** Maps `test_step_run`. */
    object StepRun {
    }

    /** Maps `python_version_specifiers`. */
    object VersionSpecifier {
    }

    object VersionOverflow {
        const val STRING_VALUE_MAX_LENGTH = SizeLimits.LEN_255
    }

    object Version {
        const val LOCAL_MAX_LENGTH = SizeLimits.LEN_255
    }

    /**
     * NOTE: only one specifier table exists in the DB (`python_version_specifiers`), mapped by
     * [VersionSpecifier] above. This object has no distinct DB counterpart as of the 2026-08-16
     * dump — flagged rather than silently removed, since a DTO (`PythonVersionSpecifier`) already
     * references it.
     */
    object PythonVersionSpecifier {
    }

    object PythonVersion {
    }

    /** Maps `package_python_requirements`. */
    object PackageRequiredPython {
    }

    object PackageDistRequirement {
        const val DEPENDENT_DIST_MAX_LENGTH = SizeLimits.LEN_255
        const val REQUIRED_DIST_MAX_LENGTH = SizeLimits.LEN_255
        const val REQUIRED_EXTRA_NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val RAW_MAX_LENGTH = SizeLimits.LEN_1024
    }

    object PackageExtra {
        const val DISTRIBUTION_MAX_LENGTH = SizeLimits.LEN_255
        const val NAME_MAX_LENGTH = SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = SizeLimits.LEN_4096
    }

    /** Maps `packages`. */
    object DistributionPackage {
        const val DISTRIBUTION_MAX_LENGTH = SizeLimits.LEN_255
        const val VERSION_STR_MAX_LENGTH = SizeLimits.LEN_255
        const val SUMMARY_MAX_LENGTH = SizeLimits.LEN_4096
        const val DESCRIPTION_CONTENT_TYPE_MAX_LENGTH = SizeLimits.LEN_1024
        const val LICENSE_MAX_LENGTH = SizeLimits.LEN_2048
        const val HOME_PAGE_MAX_LENGTH = SizeLimits.LEN_255
        const val PROJECT_URL_MAX_LENGTH = SizeLimits.LEN_255
        const val DOWNLOAD_URL_MAX_LENGTH = SizeLimits.LEN_255
        const val DOCS_URL_MAX_LENGTH = SizeLimits.LEN_255
        // keywords / description are TEXT (65,535).
    }

    object Distribution {
        /** distributions.id itself is the varchar(255) primary key. */
        const val ID_MAX_LENGTH = SizeLimits.LEN_255
    }

    object EnvMarkerLogical {
    }

    object EnvMarkerExpression {
    }

    object EnvMarkerComparison {
        const val VARIABLE_MAX_LENGTH = SizeLimits.LEN_255
        // value is TEXT (65,535).
    }
}
