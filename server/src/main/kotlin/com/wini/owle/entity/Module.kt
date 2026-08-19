package com.wini.owle.entity

import com.wini.owle.dto.StringDto
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.dto.environs.marker.EnvMarkerLogical
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.dto.environs.pkg.PackagePythonRequirement
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.tags.targets.PackageTag
import com.wini.owle.dto.tags.targets.StepTag
import com.wini.owle.dto.tags.targets.ToolTag
import com.wini.owle.dto.tags.targets.WorkflowFolderTag
import com.wini.owle.dto.tags.targets.WorkflowTag
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.dto.typing.CallableSignature
import com.wini.owle.dto.typing.ClassInheritance
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.WorkflowFolderRelation
import com.wini.owle.dto.workflows.WorkflowMetadata
import com.wini.owle.dto.workflows.WorkflowRequirement
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.delegate.StringIdDaoDelegate
import com.wini.owle.entity.delegate.ULongIdDaoDelegate
import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import com.wini.owle.entity.environs.marker.EnvMarkerLogicalEntity
import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.entity.environs.pkg.PackageDistRequirementEntity
import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.environs.version.VersionOverflowEntity
import com.wini.owle.entity.runs.step.ExecutionLogEntity
import com.wini.owle.entity.runs.step.StepTestRunEntity
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestExpectedOutcomeEntity
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.entity.runs.workflow.WorkflowExecutionEntity
import com.wini.owle.entity.steps.DataPointInjectionEntity
import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.steps.ToolEntity
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.tags.targets.PackageTagEntity
import com.wini.owle.entity.tags.targets.StepTagEntity
import com.wini.owle.entity.tags.targets.ToolTagEntity
import com.wini.owle.entity.tags.targets.WorkflowFolderTagEntity
import com.wini.owle.entity.tags.targets.WorkflowTagEntity
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.transforms.TransformOccurrenceArgEntity
import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.typing.CallableParameterEntity
import com.wini.owle.entity.typing.CallableSignatureEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeNodeRelationEntity
import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.entity.workflows.WorkflowFolderRelationEntity
import com.wini.owle.entity.workflows.WorkflowMetadataEntity
import com.wini.owle.entity.workflows.WorkflowRequirementEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.koin.core.module.Module
import org.koin.dsl.module

/*
 * DI registrations for the entity layer.
 *
 * Some entities are deliberately unbound: the tables they map gained or changed a key shape in the
 * schema reconciliation, and their DTO counterparts have not caught up yet. Specifically —
 *
 *  - DataPointWriteArg, TransformOccurrenceChain,
 *    CallableOwner, PackageExtra, MultiflowWorkflow, GrouperTag: composite-keyed, no DTO yet.
 *  - Grouper, Multiflow, TypeAlias, InquisitionStepRedirect, InquisitionJunctionRedirect,
 *    no DTO yet, or the DTO is still CompositeDto against a now ULong-keyed row.
 *  - TypeVarBound / TypeVarConstraint / TypeVarVariance: entities are now ULong-keyed but their
 *    DTOs are still CompositeDto.
 *
 * Bind these once the DTO layer is reconciled against the same source of truth.
 */

val environsEntitiesModule = module {
    single<StringEntityClass<DistributionEntity>> { DistributionEntity }
}

