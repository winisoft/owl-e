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
import com.wini.owle.dto.tags.targets.PackageTag
import com.wini.owle.dto.tags.targets.PhaseTag
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
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarConstraint
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.*
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.delegate.StringIdDaoDelegate
import com.wini.owle.entity.delegate.ULongIdDaoDelegate
import com.wini.owle.entity.delegate.impl.ClassInheritanceDaoDelegate
import com.wini.owle.entity.delegate.impl.DistributionPackageDaoDelegate
import com.wini.owle.entity.delegate.impl.PackagePythonRequirementDaoDelegate
import com.wini.owle.entity.delegate.impl.PackageTagDaoDelegate
import com.wini.owle.entity.delegate.impl.PhaseRelationDaoDelegate
import com.wini.owle.entity.delegate.impl.PhaseTagDaoDelegate
import com.wini.owle.entity.delegate.impl.StepRouteDaoDelegate
import com.wini.owle.entity.delegate.impl.StepTagDaoDelegate
import com.wini.owle.entity.delegate.impl.TagRelationDaoDelegate
import com.wini.owle.entity.delegate.impl.ToolTagDaoDelegate
import com.wini.owle.entity.delegate.impl.TransformTargetTypeDaoDelegate
import com.wini.owle.entity.delegate.impl.TypeUnionMemberDaoDelegate
import com.wini.owle.entity.delegate.impl.TypeVarBoundDaoDelegate
import com.wini.owle.entity.delegate.impl.TypeVarConstraintDaoDelegate
import com.wini.owle.entity.delegate.impl.TypeVarVarianceDaoDelegate
import com.wini.owle.entity.delegate.impl.WorkflowFolderRelationDaoDelegate
import com.wini.owle.entity.delegate.impl.WorkflowFolderTagDaoDelegate
import com.wini.owle.entity.delegate.impl.WorkflowRequirementDaoDelegate
import com.wini.owle.entity.delegate.impl.WorkflowTagDaoDelegate
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
import com.wini.owle.entity.tags.targets.PackageTagEntity
import com.wini.owle.entity.tags.targets.PhaseTagEntity
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
import com.wini.owle.entity.typing.TypeParameterEntity
import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.entity.typing.TypeVarConstraintEntity
import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.*
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.koin.core.module.Module
import org.koin.dsl.module


val environsEntitiesModule = module {
    bindULongDelegate<EnvMarkerComparison, EnvMarkerComparisonEntity>(EnvMarkerComparisonEntity)
    bindULongDelegate<EnvMarkerExpression, EnvMarkerExpressionEntity>(EnvMarkerExpressionEntity)
    bindULongDelegate<EnvMarkerLogical, EnvMarkerLogicalEntity>(EnvMarkerLogicalEntity)
    single<StringEntityClass<DistributionEntity>> { DistributionEntity }
    single<DaoDelegate<String, StringDto, Entity<String>>> {
        StringIdDaoDelegate<StringDto, Entity<String>>(get())
    }
    single<DaoDelegate<CompositeID, DistributionPackage, DistributionPackageEntity>> {
        DistributionPackageDaoDelegate
    }
    single<DaoDelegate<CompositeID, DistributionPackage, DistributionPackageEntity>> {
        DistributionPackageDaoDelegate
    }
    bindULongDelegate<PackageDistRequirement, PackageDistRequirementEntity>(PackageDistRequirementEntity)
    single<DaoDelegate<CompositeID, PackagePythonRequirement, PackagePythonRequirementEntity>> {
        PackagePythonRequirementDaoDelegate
    }

    bindULongDelegate<PythonVersion, PythonVersionEntity>(PythonVersionEntity)
    bindULongDelegate<PythonVersionSpecifier, PythonVersionSpecifierEntity>(PythonVersionSpecifierEntity)
    bindULongDelegate<Version, VersionEntity>(VersionEntity)
    bindULongDelegate<VersionOverflow, VersionOverflowEntity>(VersionOverflowEntity)
}

val runEntitiesModule = module {
    bindULongDelegate<StepTestRun, StepTestRunEntity>(StepTestRunEntity)
    bindULongDelegate<ExecutionNotification, ExecutionNotificationEntity>(ExecutionNotificationEntity)
    bindULongDelegate<BenchmarkReport, BenchmarkReportEntity>(BenchmarkReportEntity)
    bindULongDelegate<TestCase, TestCaseEntity>(TestCaseEntity)
    bindULongDelegate<TestOutcomeExpectation, TestOutcomeExpectationEntity>(TestOutcomeExpectationEntity)
    bindULongDelegate<TestInput, TestInputEntity>(TestInputEntity)
    bindULongDelegate<TestOutcomeActual, TestResultEntity>(TestResultEntity)
    bindULongDelegate<WorkflowRun, WorkflowRunEntity>(WorkflowRunEntity)
}

val stepEntitiesModule = module {
    bindULongDelegate<DataPointInjection, DataPointInjectionEntity>(DataPointInjectionEntity)
    bindULongDelegate<Inquisition, InquisitionEntity>(InquisitionEntity)
    bindULongDelegate<DataPointRead, DataPointReadEntity>(DataPointReadEntity)
    bindULongDelegate<DataPointWrite, DataPointWriteEntity>(DataPointWriteEntity)
    bindULongDelegate<ErrorWatch, ErrorWatchEntity>(ErrorWatchEntity)
    bindULongDelegate<ErrorWatchFn, ErrorWatchFnEntity>(ErrorWatchFnEntity)
    bindULongDelegate<ToolParam, ToolParamEntity>(ToolParamEntity)
    bindULongDelegate<WorkflowJunction, WorkflowJunctionEntity>(WorkflowJunctionEntity)
}

val tagEntitiesModule = module {
    bindULongDelegate<Tag, TagEntity>(TagEntity)
    single<DaoDelegate<CompositeID, TagRelation, TagRelationEntity>> {
        TagRelationDaoDelegate
    }
    single<DaoDelegate<CompositeID, PackageTag, PackageTagEntity>> {
        PackageTagDaoDelegate
    }
    single<DaoDelegate<CompositeID, PhaseTag, PhaseTagEntity>> {
        PhaseTagDaoDelegate
    }
    single<DaoDelegate<CompositeID, StepTag, StepTagEntity>> {
        StepTagDaoDelegate
    }
    single<DaoDelegate<CompositeID, ToolTag, ToolTagEntity>> {
        ToolTagDaoDelegate
    }
    single<DaoDelegate<CompositeID, WorkflowFolderTag, WorkflowFolderTagEntity>> {
        WorkflowFolderTagDaoDelegate
    }
    single<DaoDelegate<CompositeID, WorkflowTag, WorkflowTagEntity>> {
        WorkflowTagDaoDelegate
    }
}

val transformEntitiesModule = module {

    bindULongDelegate<TransformArg, TransformArgEntity>(TransformArgEntity)
    bindULongDelegate<Transform, TransformEntity>(TransformEntity)
    bindULongDelegate<TransformOccurrence, TransformOccurrenceEntity>(TransformOccurrenceEntity)
    bindULongDelegate<TransformOccurrenceArg, TransformOccurrenceArgEntity>(TransformOccurrenceArgEntity)
    single<DaoDelegate<CompositeID, TransformTargetType, TransformTargetTypeEntity>> {
        TransformTargetTypeDaoDelegate
    }

}


val typingEntitiesModule = module {

    bindULongDelegate<CallableParameter, CallableParameterEntity>(CallableParameterEntity)
    bindULongDelegate<CallableSignature, CallableSignatureEntity>(CallableSignatureEntity)
    bindULongDelegate<TypeNode, TypeNodeEntity>(TypeNodeEntity)
    single<DaoDelegate<CompositeID, ClassInheritance, TypeNodeRelationEntity>> {
        ClassInheritanceDaoDelegate
    }
    bindULongDelegate<TypeParameter, TypeParameterEntity>(TypeParameterEntity)
    bindULongDelegate<TypeSchemaRef, TypeSchemaRefEntity>(TypeSchemaRefEntity)
    single<DaoDelegate<CompositeID, TypeUnionMember, TypeUnionMemberEntity>> {
        TypeUnionMemberDaoDelegate
    }

    single<DaoDelegate<CompositeID, TypeVarBound, TypeVarBoundEntity>> {
        TypeVarBoundDaoDelegate
    }

    single<DaoDelegate<CompositeID, TypeVarConstraint, TypeVarConstraintEntity>> {
        TypeVarConstraintDaoDelegate
    }

    single<DaoDelegate<CompositeID, TypeVarVariance, TypeVarVarianceEntity>> {
        TypeVarVarianceDaoDelegate
    }

}

val userEntitiesModule = module {
    bindULongDelegate<User, UserEntity>(UserEntity)
    bindULongDelegate<PaymentMethod, PaymentMethodEntity>(PaymentMethodEntity)
}

val workflowEntitiesModule = module {

    bindULongDelegate<DataPoint, DataPointEntity>(DataPointEntity)
    bindULongDelegate<Phase, PhaseEntity>(PhaseEntity)
    single<DaoDelegate<CompositeID, PhaseRelation, PhaseRelationEntity>> {
        PhaseRelationDaoDelegate
    }
    bindULongDelegate<Step, StepEntity>(StepEntity)
    bindULongDelegate<Tool, ToolEntity>(ToolEntity)
    bindULongDelegate<Workflow, WorkflowEntity>(WorkflowEntity)
    bindULongDelegate<WorkflowFolder, WorkflowFolderEntity>(WorkflowFolderEntity)
    single<DaoDelegate<CompositeID, WorkflowFolderRelation, WorkflowFolderRelationEntity>> {
        WorkflowFolderRelationDaoDelegate
    }
    bindULongDelegate<WorkflowMetadata, WorkflowMetadataEntity>(WorkflowMetadataEntity)
    single<DaoDelegate<CompositeID, WorkflowRequirement, WorkflowRequirementEntity>> {
        WorkflowRequirementDaoDelegate
    }

    single<DaoDelegate<CompositeID, StepDefaultRoute, StepDefaultRouteEntity>> {
        StepRouteDaoDelegate
    }

}

inline fun <reified DTO : ULongDto, reified E : ULongEntity> Module.bindULongDelegate(
    entityClass: ULongEntityClass<E>
) {
    single<ULongEntityClass<E>> { entityClass }
    single<DaoDelegate<ULong, DTO, E>> { ULongIdDaoDelegate(entityClass) }
}
