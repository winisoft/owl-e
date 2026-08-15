package com.wini.owle.schema

import kotlinx.datetime.Instant


object Schema {

    object Utils {
        val instant = Instant.parse("2024-12-20T10:00:00Z")
    }



    object SizeLimits {
        const val LEN_127 = 127
        const val LEN_255 = 255
        const val LEN_512 = 512
        const val LEN_1024 = 1024

        const val LEN_8192 = 8192
        const val LEN_16384 = 16384
        const val LEN_16000 = 16000
        const val LEN_64000 = 64_000
        const val LEN_5012 = 5012
    }

    object DataPoint {

    }
    object Phase {

    }
    object Step {

    }
    object Tool {

    }
    object Workflow {
        const val NAME_MAX_LENGTH = Schema.SizeLimits.LEN_255
        const val DESC_MAX_LENGTH = Schema.SizeLimits.LEN_1024
    }
    object WorkflowFolder {
    }
    object WorkflowMetadata {
    }
    object WorkflowRequirements {
    }
    object User {
    }
    object PaymentMethod {
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
    }
    object TypeParameter {
    }
    object TypeNode {
    }
    object ClassInheritance {
    }
    object CallableSignature {
    }
    object CallableParameter {
    }
    object TransformTargetType {
    }
    object TransformOccurrenceArg {
    }
    object TransformOccurrence {
    }
    object TransformArg {
    }
    object Transform {
    }
    object TagRelation {
    }
    object Tag {
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
    object PhaseTag {
    }
    object PackageTag {
    }
    object DistributionTag {
    }
    object WorkflowJunction {
    }
    object ToolParam {
    }
    object Inquisition {
    }
    object ErrorWatchFn {
    }
    object ErrorWatch {
    }
    object DataPointWriteLink {
    }
    object DataPointWrite {
    }
    object DataPointRead {
    }
    object DataPointInjection {
    }
    object WorkflowTestResult {
    }
    object WorkflowRun {
    }
    object TestInput {
    }
    object TestExpectation {
    }
    object TestCase {
    }
    object BenchmarkReport {
    }
    object StepRunLogLine {
    }
    object StepRun {
    }
    object VersionSpecifier {
    }
    object VersionOverflow {
    }
    object Version {
    }
    object PythonVersionSpecifier {
    }
    object PythonVersion {
    }
    object PackageRequiredPython {
    }
    object PackageDistRequirement {
    }
    object DistributionPackage {
    }
    object Distribution {
    }
    object EnvMarkerLogical {
    }
    object EnvMarkerExpression {
    }
    object EnvMarkerComparison {
    }
}