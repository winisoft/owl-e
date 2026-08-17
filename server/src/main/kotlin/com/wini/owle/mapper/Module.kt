package com.wini.owle.mapper

import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.dto.environs.marker.EnvMarkerLogical
import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.dto.environs.pkg.PackagePythonRequirement
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.dto.environs.version.Version
import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestOutcomeExpectation
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.runs.workflow.WorkflowRun
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.tags.targets.StepTag
import com.wini.owle.dto.tags.targets.WorkflowTag
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.dto.typing.CallableSignature
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarConstraint
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.dto.workflows.PhaseRelation
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.WorkflowFolderRelation
import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import com.wini.owle.entity.environs.marker.EnvMarkerLogicalEntity
import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.entity.environs.pkg.PackageDistRequirementEntity
import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.environs.version.PythonVersionSpecifierEntity
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.environs.version.VersionOverflowEntity
import com.wini.owle.entity.runs.step.StepTestRunEntity
import com.wini.owle.entity.runs.step.ExecutionNotificationEntity
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestOutcomeExpectationEntity
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.entity.steps.DataPointInjectionEntity
import com.wini.owle.entity.steps.DataPointReadEntity
import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.tags.targets.StepTagEntity
import com.wini.owle.entity.tags.targets.WorkflowTagEntity
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.transforms.TransformOccurrenceArgEntity
import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.typing.CallableParameterEntity
import com.wini.owle.entity.typing.CallableSignatureEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeParameterEntity
import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.entity.typing.TypeVarConstraintEntity
import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.entity.workflows.PhaseRelationEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.entity.workflows.WorkflowFolderRelationEntity
import com.wini.owle.mapper.environs.marker.EnvMarkerComparisonMapper
import com.wini.owle.mapper.environs.marker.EnvMarkerExpressionMapper
import com.wini.owle.mapper.environs.marker.EnvMarkerLogicalMapper
import com.wini.owle.mapper.environs.pkg.DistributionMapper
import com.wini.owle.mapper.environs.pkg.DistributionPackageMapper
import com.wini.owle.mapper.environs.pkg.PackageDistRequirementMapper
import com.wini.owle.mapper.environs.pkg.PackagePythonRequiredMapper
import com.wini.owle.mapper.environs.version.PythonVersionMapper
import com.wini.owle.mapper.environs.version.PythonVersionSpecifierMapper
import com.wini.owle.mapper.environs.version.VersionMapper
import com.wini.owle.mapper.environs.version.VersionOverflowMapper
import com.wini.owle.mapper.runs.BenchmarkReportMapper
import com.wini.owle.mapper.runs.ExecutionLogMapper
import com.wini.owle.mapper.runs.StepTestRunMapper
import com.wini.owle.mapper.runs.TestCasesMapper
import com.wini.owle.mapper.runs.TestExpectationMapper
import com.wini.owle.mapper.runs.TestInputMapper
import com.wini.owle.mapper.runs.TestOutcomeActualMapper
import com.wini.owle.mapper.runs.WorkflowRunMapper
import com.wini.owle.mapper.steps.DataPointInjectionMapper
import com.wini.owle.mapper.steps.DataPointReadMapper
import com.wini.owle.mapper.steps.DataPointWriteMapper
import com.wini.owle.mapper.steps.ErrorWatchFnMapper
import com.wini.owle.mapper.steps.ErrorWatchMapper
import com.wini.owle.mapper.steps.InquisitionsMapper
import com.wini.owle.mapper.steps.ToolParamsMapper
import com.wini.owle.mapper.steps.WorkflowJunctionsMapper
import com.wini.owle.mapper.tags.TagRelationsMapper
import com.wini.owle.mapper.tags.TagsMapper
import com.wini.owle.mapper.tags.targets.StepTagMapper
import com.wini.owle.mapper.tags.targets.WorkflowTagMapper
import com.wini.owle.mapper.transforms.TransformArgMapper
import com.wini.owle.mapper.transforms.TransformMapper
import com.wini.owle.mapper.transforms.TransformOccurrenceArgMapper
import com.wini.owle.mapper.transforms.TransformOccurrenceMapper
import com.wini.owle.mapper.transforms.TransformTargetTypeMapper
import com.wini.owle.mapper.typing.CallableParameterMapper
import com.wini.owle.mapper.typing.CallableSignatureMapper
import com.wini.owle.mapper.typing.TypeNodeMapper
import com.wini.owle.mapper.typing.TypeParameterMapper
import com.wini.owle.mapper.typing.TypeSchemaRefMapper
import com.wini.owle.mapper.typing.TypeUnionMemberMapper
import com.wini.owle.mapper.typing.TypeVarBoundMapper
import com.wini.owle.mapper.typing.TypeVarConstraintMapper
import com.wini.owle.mapper.typing.TypeVarVarianceMapper
import com.wini.owle.mapper.user.PaymentMethodsMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.DataPointMapper
import com.wini.owle.mapper.workflows.PhaseMapper
import com.wini.owle.mapper.workflows.PhaseRelationMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.ToolMapper
import com.wini.owle.mapper.workflows.WorkflowFolderMapper
import com.wini.owle.mapper.workflows.WorkflowFolderRelationMapper
import com.wini.owle.mapper.workflows.WorkflowMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.koin.dsl.module


val environsMapperModule = module {
    single<DbMapper<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity>> { EnvMarkerComparisonMapper(get(), get()) }
    single<DbMapper<ULong, EnvMarkerExpression, EnvMarkerExpressionEntity>> { EnvMarkerExpressionMapper(get(), get()) }
    single<DbMapper<ULong, EnvMarkerLogical, EnvMarkerLogicalEntity>> { EnvMarkerLogicalMapper(get(), get(), get()) }

    single<DbMapper<String, Distribution, DistributionEntity>> { DistributionMapper(get()) }
    single<DbMapper<CompositeID, DistributionPackage, DistributionPackageEntity>> { DistributionPackageMapper(get(), get(), get(), get()) }
    single<DbMapper<ULong, PackageDistRequirement, PackageDistRequirementEntity>> { PackageDistRequirementMapper(get(), get(), get()) }
    single<DbMapper<CompositeID, PackagePythonRequirement, PackagePythonRequirementEntity>> { PackagePythonRequiredMapper(get(), get(), get()) }

    single<DbMapper<ULong, PythonVersion, PythonVersionEntity>> { PythonVersionMapper(get(), get()) }
    single<DbMapper<ULong, PythonVersionSpecifier, PythonVersionSpecifierEntity>> { PythonVersionSpecifierMapper(get(), get()) }
    single<DbMapper<ULong, Version, VersionEntity>> { VersionMapper(get(), get()) }
    single<DbMapper<ULong, VersionOverflow, VersionOverflowEntity>> { VersionOverflowMapper(get(), get()) }
}

val runsMappersModule = module {
    single<DbMapper<ULong, ExecutionNotification, ExecutionNotificationEntity>> { ExecutionLogMapper(get(), get(), get()) }
    single<DbMapper<ULong, StepTestRun, StepTestRunEntity>> { StepTestRunMapper(get(), get(), get()) }

    single<DbMapper<ULong, BenchmarkReport, BenchmarkReportEntity>> { BenchmarkReportMapper(get(), get()) }
    single<DbMapper<ULong, TestCase, TestCaseEntity>> { TestCasesMapper(get(), get(), get(), get(), get(), get(), get()) }
    single<DbMapper<ULong, TestOutcomeExpectation, TestOutcomeExpectationEntity>> { TestExpectationMapper(get(), get()) }
    single<DbMapper<ULong, TestInput, TestInputEntity>> { TestInputMapper(get(), get(), get()) }

    single<DbMapper<ULong, TestOutcomeActual, TestResultEntity>> { TestOutcomeActualMapper(get(), get()) }
    single<DbMapper<ULong, WorkflowRun, WorkflowRunEntity>> { WorkflowRunMapper(get(), get(), get(), get(), get()) }
}

val stepMappersModule = module {
    single<DbMapper<ULong, DataPointInjection, DataPointInjectionEntity>> { DataPointInjectionMapper(get(), get(), get()) }
    single<DbMapper<ULong, DataPointRead, DataPointReadEntity>> { DataPointReadMapper(get()) }
    single<DbMapper<ULong, DataPointWrite, DataPointWriteEntity>> { DataPointWriteMapper(get(), get(), get()) }
    single<DbMapper<ULong, ErrorWatch, ErrorWatchEntity>> { ErrorWatchMapper(get(), get()) }
    single<DbMapper<ULong, ErrorWatchFn, ErrorWatchFnEntity>> { ErrorWatchFnMapper(get(), get(), get()) }
    single<DbMapper<ULong, Inquisition, InquisitionEntity>> { InquisitionsMapper(get(), get(), get(), get()) }
    single<DbMapper<ULong, ToolParam, ToolParamEntity>> { ToolParamsMapper(get(), get(), get()) }
    single<DbMapper<ULong, WorkflowJunction, WorkflowJunctionEntity>> { WorkflowJunctionsMapper(get(), get(), get(), get()) }
}

val tagMappersModule = module {
    single<DbMapper<CompositeID, StepTag, StepTagEntity>> { StepTagMapper(get()) }
    single<DbMapper<CompositeID, WorkflowTag, WorkflowTagEntity>> { WorkflowTagMapper(get()) }
    single<DbMapper<CompositeID, TagRelation, TagRelationEntity>> { TagRelationsMapper(get()) }
    single<DbMapper<ULong, Tag, TagEntity>> { TagsMapper(get(), get()) }
}

val transformMappersModule = module {
    single<DbMapper<ULong, TransformArg, TransformArgEntity>> { TransformArgMapper(get(), get(), get()) }
    single<DbMapper<ULong, Transform, TransformEntity>> { TransformMapper(get(), get()) }
    single<DbMapper<ULong, TransformOccurrenceArg, TransformOccurrenceArgEntity>> { TransformOccurrenceArgMapper(get(), get(), get()) }
    single<DbMapper<ULong, TransformOccurrence, TransformOccurrenceEntity>> { TransformOccurrenceMapper(get(), get(), get(), get(), get()) }
    single<DbMapper<CompositeID, TransformTargetType, TransformTargetTypeEntity>> { TransformTargetTypeMapper(get()) }
}

val typingMappersModule = module {
    single<DbMapper<ULong, CallableParameter, CallableParameterEntity>> { CallableParameterMapper(get(), get(), get()) }
    single<DbMapper<ULong, CallableSignature, CallableSignatureEntity>> { CallableSignatureMapper(get(), get()) }
    single<DbMapper<ULong, TypeNode, TypeNodeEntity>> { TypeNodeMapper(get(), get(), get(), get(), get(), get()) }
    single<DbMapper<ULong, TypeParameter, TypeParameterEntity>> { TypeParameterMapper(get(), get()) }
    single<DbMapper<ULong, TypeSchemaRef, TypeSchemaRefEntity>> { TypeSchemaRefMapper(get(), get()) }
    single<DbMapper<CompositeID, TypeUnionMember, TypeUnionMemberEntity>> { TypeUnionMemberMapper(get(), get()) }
    single<DbMapper<CompositeID, TypeVarBound, TypeVarBoundEntity>> { TypeVarBoundMapper(get(), get()) }
    single<DbMapper<CompositeID, TypeVarConstraint, TypeVarConstraintEntity>> { TypeVarConstraintMapper(get(), get()) }
    single<DbMapper<CompositeID, TypeVarVariance, TypeVarVarianceEntity>> { TypeVarVarianceMapper(get(), get()) }
}

val userMappersModule = module {
    single<DbMapper<ULong, PaymentMethod, PaymentMethodEntity>> { PaymentMethodsMapper(get(), get(), get()) }
    single<DbMapper<ULong, User, UserEntity>> { UsersMapper(get()) }
}

val workflowMappersModule = module {
    single<DbMapper<ULong, DataPoint, DataPointEntity>> { DataPointMapper(get(), get(), get()) }
    single<DbMapper<ULong, Grouper, PhaseEntity>> { PhaseMapper(get(), get()) }
    single<DbMapper<CompositeID, PhaseRelation, PhaseRelationEntity>> { PhaseRelationMapper(get(), get()) }
    single<DbMapper<ULong, Step, StepEntity>> { StepsMapper(get(), get(), get(), get()) }
    single<DbMapper<ULong, Tool, ToolEntity>> { ToolMapper(get(), get()) }
    single<DbMapper<ULong, PythonVersion, PythonVersionEntity>> { PythonVersionMapper(get(), get()) }
    single<DbMapper<CompositeID, WorkflowFolderRelation, WorkflowFolderRelationEntity>> { WorkflowFolderRelationMapper(get(), get()) }
    single<DbMapper<ULong, Workflow, WorkflowEntity>> { WorkflowMapper(get(), get()) }
    single<DbMapper<ULong, WorkflowFolder, WorkflowFolderEntity>> { WorkflowFolderMapper(get()) }
}
