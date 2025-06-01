package com.wini.owle.repo

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
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.runs.test.TestCaseEntity
import com.wini.owle.entity.runs.test.TestOutcomeExpectationEntity
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.runs.workflow.TestResultEntity
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.entity.steps.*
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.transforms.*
import com.wini.owle.entity.typing.*
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.*
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass
import org.koin.core.module.Module
import org.koin.dsl.module


val environsReposModule = module {
    bindGenericRepo<ULong, EnvMarkerComparisonEntity>(EnvMarkerComparisonEntity)
    bindGenericRepo<ULong, EnvMarkerExpressionEntity>(EnvMarkerExpressionEntity)
    bindGenericRepo<ULong, EnvMarkerLogicalEntity>(EnvMarkerLogicalEntity)

    bindGenericRepo<String, DistributionEntity>(DistributionEntity)
    bindGenericRepo<CompositeID, DistributionPackageEntity>(DistributionPackageEntity)
    bindGenericRepo<ULong, PackageDistRequirementEntity>(PackageDistRequirementEntity)
    bindGenericRepo<CompositeID, PackagePythonRequirementEntity>(PackagePythonRequirementEntity)

    bindGenericRepo<ULong, PythonVersionEntity>(PythonVersionEntity)
    bindGenericRepo<ULong, PythonVersionSpecifierEntity>(PythonVersionSpecifierEntity)
    bindGenericRepo<ULong, VersionEntity>(VersionEntity)
    bindGenericRepo<ULong, VersionOverflowEntity>(VersionOverflowEntity)
}

val runsReposModule = module {
    bindGenericRepo<ULong, BenchmarkReportEntity>(BenchmarkReportEntity)
    bindGenericRepo<ULong, TestCaseEntity>(TestCaseEntity)
    bindGenericRepo<ULong, TestInputEntity>(TestInputEntity)
    bindGenericRepo<ULong, TestOutcomeExpectationEntity>(TestOutcomeExpectationEntity)
    bindGenericRepo<ULong, TestResultEntity>(TestResultEntity)
}

val stepsReposModule = module {
    bindGenericRepo<ULong, ToolEntity>(ToolEntity)
    bindGenericRepo<ULong, ToolParamEntity>(ToolParamEntity)
    bindGenericRepo<ULong, ErrorWatchEntity>(ErrorWatchEntity)
    bindGenericRepo<ULong, ErrorWatchFnEntity>(ErrorWatchFnEntity)
    bindGenericRepo<ULong, InquisitionEntity>(InquisitionEntity)
    bindGenericRepo<ULong, WorkflowJunctionEntity>(WorkflowJunctionEntity)
    bindGenericRepo<ULong, DataPointInjectionEntity>(DataPointInjectionEntity)
    bindGenericRepo<ULong, DataPointWriteEntity>(DataPointWriteEntity)
    bindGenericRepo<ULong, DataPointReadEntity>(DataPointReadEntity)
    bindGenericRepo<ULong, DataPointReadEntity>(DataPointReadEntity)
}

val tagReposModule = module {
    bindGenericRepo<CompositeID, TagRelationEntity>(TagRelationEntity)
    bindGenericRepo<ULong, TagEntity>(TagEntity)
}

val transformsReposModule = module {
    bindGenericRepo<CompositeID, TransformTargetTypeEntity>(TransformTargetTypeEntity)
    bindGenericRepo<ULong, TransformEntity>(TransformEntity)
    bindGenericRepo<ULong, TransformArgEntity>(TransformArgEntity)
    bindGenericRepo<ULong, TransformOccurrenceEntity>(TransformOccurrenceEntity)
    bindGenericRepo<ULong, TransformOccurrenceArgEntity>(TransformOccurrenceArgEntity)
}

val typingReposModule = module {
    bindGenericRepo<CompositeID, TypeVarBoundEntity>(TypeVarBoundEntity)
    bindGenericRepo<CompositeID, TypeUnionMemberEntity>(TypeUnionMemberEntity)
    bindGenericRepo<CompositeID, TypeVarVarianceEntity>(TypeVarVarianceEntity)
    bindGenericRepo<ULong, TypeNodeEntity>(TypeNodeEntity)
    bindGenericRepo<ULong, TypeParameterEntity>(TypeParameterEntity)
    bindGenericRepo<ULong, TypeSchemaRefEntity>(TypeSchemaRefEntity)
}

val userReposModule = module {
    bindGenericRepo<ULong, UserEntity>(UserEntity)
    bindGenericRepo<ULong, PaymentMethodEntity>(PaymentMethodEntity)
}

val workflowReposModule = module {
    bindGenericRepo<ULong, WorkflowFolderEntity>(WorkflowFolderEntity)
    bindGenericRepo<ULong, WorkflowEntity>(WorkflowEntity)
    bindGenericRepo<ULong, WorkflowMetadataEntity>(WorkflowMetadataEntity)
    bindGenericRepo<ULong, PhaseEntity>(PhaseEntity)
    bindGenericRepo<ULong, StepEntity>(StepEntity)
    bindGenericRepo<ULong, DataPointEntity>(DataPointEntity)
    bindGenericRepo<ULong, WorkflowRunEntity>(WorkflowRunEntity)
    bindGenericRepo<ULong, StepTestRunEntity>(StepTestRunEntity)
}


inline fun <reified ID : Any, reified T : Entity<ID>> Module.bindGenericRepo(
    entityClass: EntityClass<ID, T>
) {
    single<EntityClass<ID, T>> { entityClass }
    single<GenericRepoImpl<ID, T>> { GenericRepoImpl(get()) }
    single<OwleRepo<ID, T>> { get<GenericRepoImpl<ID, T>>() }
}
