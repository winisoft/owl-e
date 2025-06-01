package com.wini.owle.services

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.StringDto
import com.wini.owle.dto.ULongDto
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
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Phase
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.entity.delegate.DaoDelegate
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
import com.wini.owle.entity.runs.test.TestInputEntity
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.entity.steps.WorkflowJunctionEntity
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.transforms.TransformOccurrenceArgEntity
import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeParameterEntity
import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.entity.user.PaymentMethodEntity
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.mapper.DbMapper
import com.wini.owle.repo.OwleRepo
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.koin.dsl.module
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import com.wini.owle.entity.steps.DataPointInjectionEntity
import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.schema.Schema
import com.wini.owle.services.workflows.WorkflowHierarchyService
import com.wini.owle.services.workflows.impl.WorkflowHierarchyServiceImpl
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.ULongEntity

val environsServicesModule = module {
    bindGenericULongService<EnvMarkerLogical, EnvMarkerLogicalEntity> {
        get<DbMapper<ULong, EnvMarkerLogical, EnvMarkerLogicalEntity>>()
    }
    bindGenericULongService<EnvMarkerExpression, EnvMarkerExpressionEntity> {
        get<DbMapper<ULong, EnvMarkerExpression, EnvMarkerExpressionEntity>>()
    }
    bindGenericULongService<EnvMarkerComparison, EnvMarkerComparisonEntity> {
        get<DbMapper<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity>>()
    }

    bindGenericStringIdService<Distribution, DistributionEntity> {
        get<DbMapper<String, Distribution, DistributionEntity>>()
    }
    bindCompositeIdGenericService<DistributionPackage, DistributionPackageEntity> {
        get<DbMapper<CompositeID, DistributionPackage, DistributionPackageEntity>>()
    }
    bindGenericULongService<PackageDistRequirement, PackageDistRequirementEntity> {
        get<DbMapper<ULong, PackageDistRequirement, PackageDistRequirementEntity>>()
    }
    bindCompositeIdGenericService<PackagePythonRequirement, PackagePythonRequirementEntity> {
        get<DbMapper<CompositeID, PackagePythonRequirement, PackagePythonRequirementEntity>>()
    }

    bindGenericULongService<PythonVersionSpecifier, PythonVersionSpecifierEntity> {
        get<DbMapper<ULong, PythonVersionSpecifier, PythonVersionSpecifierEntity>>()
    }
    bindGenericULongService<PythonVersion, PythonVersionEntity> {
        get<DbMapper<ULong, PythonVersion, PythonVersionEntity>>()
    }
    bindGenericULongService<Version, VersionEntity> {
        get<DbMapper<ULong, Version, VersionEntity>>()
    }
    bindGenericULongService<VersionOverflow, VersionOverflowEntity> {
        get<DbMapper<ULong, VersionOverflow, VersionOverflowEntity>>()
    }
}

val runsServicesModule = module {

    bindGenericULongService<BenchmarkReport, BenchmarkReportEntity> {
        get<DbMapper<ULong, BenchmarkReport, BenchmarkReportEntity>>()
    }
    bindGenericULongService<StepTestRun, StepTestRunEntity> {
        get<DbMapper<ULong, StepTestRun, StepTestRunEntity>>()
    }
    bindGenericULongService<TestCase, TestCaseEntity> {
        get<DbMapper<ULong, TestCase, TestCaseEntity>>()
    }
//    bindGenericULongService<TestExpectation, TestExpectationEntity>({
//        get<DbMapper<ULong, TestExpectedOutcome, TestExpectedOutcomeEntity>>()
//    })
    bindGenericULongService<TestInput, TestInputEntity> {
        get<DbMapper<ULong, TestInput, TestInputEntity>>()
    }
}

val stepServicesModule = module {
    bindGenericULongService<ErrorWatchFn, ErrorWatchFnEntity> {
        get<DbMapper<ULong, ErrorWatchFn, ErrorWatchFnEntity>>()
    }
    bindGenericULongService<ErrorWatch, ErrorWatchEntity> {
        get<DbMapper<ULong, ErrorWatch, ErrorWatchEntity>>()
    }
    bindGenericULongService<Inquisition, InquisitionEntity> {
        get<DbMapper<ULong, Inquisition, InquisitionEntity>>()
    }
    bindGenericULongService<WorkflowJunction, WorkflowJunctionEntity> {
        get<DbMapper<ULong, WorkflowJunction, WorkflowJunctionEntity>>()
    }
    bindGenericULongService<DataPointWrite, DataPointWriteEntity> {
        get<DbMapper<ULong, DataPointWrite, DataPointWriteEntity>>()
    }
    bindGenericULongService<DataPointInjection, DataPointInjectionEntity> {
        get<DbMapper<ULong, DataPointInjection, DataPointInjectionEntity>>()
    }
}

val tagServicesModule = module {
    bindGenericULongService<Tag, TagEntity> {
        get<DbMapper<ULong, Tag, TagEntity>>()
    }
    bindCompositeIdGenericService<TagRelation, TagRelationEntity> {
        get<DbMapper<CompositeID, TagRelation, TagRelationEntity>>()
    }
}

val transformServicesModule = module {
    bindGenericULongService<TransformOccurrenceArg, TransformOccurrenceArgEntity> {
        get<DbMapper<ULong, TransformOccurrenceArg, TransformOccurrenceArgEntity>>()
    }
    bindGenericULongService<TransformOccurrence, TransformOccurrenceEntity> {
        get<DbMapper<ULong, TransformOccurrence, TransformOccurrenceEntity>>()
    }
    bindGenericULongService<TransformArg, TransformArgEntity> {
        get<DbMapper<ULong, TransformArg, TransformArgEntity>>()
    }
    bindGenericULongService<Transform, TransformEntity> {
        get<DbMapper<ULong, Transform, TransformEntity>>()
    }
}

val typingServicesModule = module {
    bindGenericULongService<TypeNode, TypeNodeEntity> {
        get<DbMapper<ULong, TypeNode, TypeNodeEntity>>()
    }
    bindCompositeIdGenericService<TypeVarBound, TypeVarBoundEntity> {
        get<DbMapper<CompositeID, TypeVarBound, TypeVarBoundEntity>>()
    }
    bindGenericULongService<TypeParameter, TypeParameterEntity> {
        get<DbMapper<ULong, TypeParameter, TypeParameterEntity>>()
    }
    bindGenericULongService<TypeSchemaRef, TypeSchemaRefEntity> {
        get<DbMapper<ULong, TypeSchemaRef, TypeSchemaRefEntity>>()
    }
    bindCompositeIdGenericService<TypeVarVariance, TypeVarVarianceEntity> {
        get<DbMapper<CompositeID, TypeVarVariance, TypeVarVarianceEntity>>()
    }
    bindCompositeIdGenericService<TypeUnionMember, TypeUnionMemberEntity> {
        get<DbMapper<CompositeID, TypeUnionMember, TypeUnionMemberEntity>>()
    }
    bindCompositeIdGenericService<TransformTargetType, TransformTargetTypeEntity> {
        get<DbMapper<CompositeID, TransformTargetType, TransformTargetTypeEntity>>()
    }
    bindCompositeIdGenericService<TypeVarVariance, TypeVarVarianceEntity> {
        get<DbMapper<CompositeID, TypeVarVariance, TypeVarVarianceEntity>>()
    }
}

val userServicesModule = module {
    bindGenericULongService<User, UserEntity> {
        get<DbMapper<ULong, User, UserEntity>>()
    }
    bindGenericULongService<PaymentMethod, PaymentMethodEntity> {
        get<DbMapper<ULong, PaymentMethod, PaymentMethodEntity>>()
    }
}

val workflowServicesModule = module {
    bindGenericULongService<Phase, PhaseEntity> {
        get<DbMapper<ULong, Phase, PhaseEntity>>()
    }
    bindGenericULongService<Step, StepEntity> {
        get<DbMapper<ULong, Step, StepEntity>>()
    }
    bindGenericULongService<ToolParam, ToolParamEntity> {
        get<DbMapper<ULong, ToolParam, ToolParamEntity>>()
    }
    bindGenericULongService<Tool, ToolEntity> {
        get<DbMapper<ULong, Tool, ToolEntity>>()
    }
    bindGenericULongService<WorkflowFolder, WorkflowFolderEntity> {
        get<DbMapper<ULong, WorkflowFolder, WorkflowFolderEntity>>()
    }
    bindGenericULongService<Workflow, WorkflowEntity> {
        get<DbMapper<ULong, Workflow, WorkflowEntity>>()
    }
    single<WorkflowHierarchyService> {
        WorkflowHierarchyServiceImpl(
            folderRepo = get<OwleRepo<ULong, WorkflowFolderEntity>>(),
            workflowRepo = get<OwleRepo<ULong, WorkflowEntity>>(),
            folderMapper = get<DbMapper<ULong, WorkflowFolder, WorkflowFolderEntity>>(),
            workflowMapper = get<DbMapper<ULong, Workflow, WorkflowEntity>>()
        )
    }
}



inline fun <
        reified DTO : StringDto,
        reified E : Entity<String>
        > Module.bindGenericStringIdService(
    noinline mapper: Scope.() -> DbMapper<String, DTO, E>
) {
    single<GenericServiceImpl<String, DTO, E>> {
        GenericServiceImpl(
            repository = get<OwleRepo<String, E>>(),
            mapper = mapper(),
            daoDelegate = get<DaoDelegate<String, DTO, E>>()
        )
    }
    single<Service<String, DTO>> { get<GenericServiceImpl<String, DTO, E>>() }
}

inline fun <
        reified DTO : ULongDto,
        reified E : ULongEntity
        > Module.bindGenericULongService(
    noinline mapper: Scope.() -> DbMapper<ULong, DTO, E>
) {
    single<GenericServiceImpl<ULong, DTO, E>> {
        GenericServiceImpl(
            repository = get<OwleRepo<ULong, E>>(),
            mapper = mapper(),
            daoDelegate = get<DaoDelegate<ULong, DTO, E>>()
        )
    }
    single<Service<ULong, DTO>> { get<GenericServiceImpl<ULong, DTO, E>>() }
}

inline fun <
        reified DTO : CompositeDto,
        reified E : CompositeEntity
        > Module.bindCompositeIdGenericService(
    noinline mapper: Scope.() -> DbMapper<CompositeID, DTO, E>
) {
    single<GenericServiceImpl<CompositeID, DTO, E>> {
        GenericServiceImpl(
            repository = get<OwleRepo<CompositeID, E>>(),
            mapper = mapper(),
            daoDelegate = get<DaoDelegate<CompositeID, DTO, E>>()
        )
    }
    single<Service<CompositeID, DTO>> { get<GenericServiceImpl<CompositeID, DTO, E>>() }
}
