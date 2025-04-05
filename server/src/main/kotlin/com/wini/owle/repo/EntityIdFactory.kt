package com.wini.owle.repo

import com.wini.owle.entity.ExecutionLogs
import com.wini.owle.entity.*
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable


abstract class EntityIdFactory<ID: Any>(val table: IdTable<ID>) {

    fun build(id: ID): EntityID<ID> = EntityID(id, table)

}

object PackageRepoIdFactory: EntityIdFactory<Long>(PackageRepositories)
object PackageIdFactory: EntityIdFactory<Long>(Packages)
object ToolPackageDependencyIdFactory: EntityIdFactory<CompositeID>(ToolPackageDependencies)
object VersionSpecIdFactory: EntityIdFactory<Long>(VersionSpecs)
object BenchmarkingDataIdFactory: EntityIdFactory<Long>(BenchmarkingData)
object ExecutionLogsIdFactory: EntityIdFactory<Long>(ExecutionLogs)
object StepBenchmarkingDataIdFactory: EntityIdFactory<Long>(StepBenchmarkingDatas)
object StepExecutionIdFactory: EntityIdFactory<Long>(StepExecutions)
object TestCaseIdFactory: EntityIdFactory<Long>(TestCases)
object TestExpectedOutcomeIdFactory: EntityIdFactory<Long>(TestExpectedOutcomes)
object TestInputIdFactory: EntityIdFactory<Long>(TestInputs)
object TestResultIdFactory: EntityIdFactory<Long>(TestResults)
object WorkflowExecutionIdFactory: EntityIdFactory<Long>(WorkflowExecutions)
object ErrorWatchFnIdFactory: EntityIdFactory<Long>(ErrorWatchFns)
object ErrorWatchIdFactory: EntityIdFactory<Long>(ErrorWatches)
object InquisitionIdFactory: EntityIdFactory<Long>(Inquisitions)
object PreReqInjectionIdFactory: EntityIdFactory<Long>(PrereqInjections)
object ProvisionDataPointIdFactory: EntityIdFactory<CompositeID>(ProvisionDataPoints)
object ProvisionIdFactory: EntityIdFactory<Long>(Provisions)
object RevisionIdFactory: EntityIdFactory<Long>(Revisions)
object WorkflowJunctionIdFactory: EntityIdFactory<Long>(WorkflowJunctions)
object AppliedTransformArgIdFactory: EntityIdFactory<Long>(AppliedTransformArgs)
object AppliedTransformIdFactory: EntityIdFactory<Long>(AppliedTransforms)
object TransformationArgIdFactory: EntityIdFactory<Long>(TransformationArgs)
object TransformationIdFactory: EntityIdFactory<Long>(Transformations)
object TransformTargetTypeIdFactory: EntityIdFactory<CompositeID>(TransformTargetTypes)
object TypeBoundIdFactory: EntityIdFactory<Long>(TypeBounds)
object TypeDeclarationIdFactory: EntityIdFactory<Long>(TypeDeclarations)
object TypeDefinitionIdFactory: EntityIdFactory<Long>(TypeDefinitions)
object TypeParameterIdFactory: EntityIdFactory<Long>(TypeParameters)
object TypeSchemaRefIdFactory: EntityIdFactory<Long>(TypeSchemaRefs)
object TypeUnionMemberIdFactory: EntityIdFactory<CompositeID>(TypeUnionMembers)
object TypeVarianceIdFactory: EntityIdFactory<Long>(TypeVariances)
object PaymentMethodIdFactory: EntityIdFactory<Long>(PaymentMethods)
object TagRelationIdFactory: EntityIdFactory<CompositeID>(TagRelations)
object TagIdFactory: EntityIdFactory<Long>(Tags)
object UserIdFactory: EntityIdFactory<Long>(Users)
object DataPointIdFactory: EntityIdFactory<Long>(DataPoints)
object PhaseIdFactory: EntityIdFactory<Long>(Phases)
object StepIdFactory: EntityIdFactory<Long>(Steps)
object ToolParamIdFactory: EntityIdFactory<Long>(ToolParams)
object ToolIdFactory: EntityIdFactory<Long>(Tools)
object WorkflowFolderIdFactory: EntityIdFactory<Long>(WorkflowFolders)
object WorkflowMetadataIdFactory: EntityIdFactory<Long>(WorkflowMetadata)
object WorkflowIdFactory: EntityIdFactory<Long>(Workflows)
