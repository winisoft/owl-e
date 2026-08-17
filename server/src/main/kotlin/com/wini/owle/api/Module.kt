package com.wini.owle.api

import com.wini.owle.dll.DLL
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
import com.wini.owle.dto.runs.workflow.WorkflowRun
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.steps.DataPointWrite
import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.steps.DataPointRead
import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.dto.steps.WorkflowJunction
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
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
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.entity.tags.TagRelationEntity
import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.typing.TypeUnionMemberEntity
import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.entity.typing.TypeVarConstraintEntity
import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.validators.DtoValidator
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.koin.dsl.module


val environsApiModule = module {
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, EnvMarkerExpression>>(),
            get<DtoValidator<EnvMarkerExpression>>(),
            "marker-expression",
            EnvMarkerExpression.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, EnvMarkerComparison>>(),
            get<DtoValidator<EnvMarkerComparison>>(),
            "marker-comparison",
            EnvMarkerComparison.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, EnvMarkerLogical>>(),
            get<DtoValidator<EnvMarkerLogical>>(),
            "marker-logical",
            EnvMarkerLogical.serializer()
        )
    }

    single<KtorApi<String>> {
        StringIdEndpointImpl(
            get<DLL<String, Distribution>>(),
            get<DtoValidator<Distribution>>(),
            "distribution",
            Distribution.serializer()
        )
    }
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID,  DistributionPackage>>(),
            get<DtoValidator<DistributionPackage>>(),
            get<CompositeIdDaoDelegate<DistributionPackage, DistributionPackageEntity>>(),
            "distribution-package",
            DistributionPackage.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, PackageDistRequirement>>(),
            get<DtoValidator<PackageDistRequirement>>(),
            "package-dist-requirement",
            PackageDistRequirement.serializer()
        )
    }
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID, PackagePythonRequirement>>(),
            get<DtoValidator<PackagePythonRequirement>>(),
            get<CompositeIdDaoDelegate<PackagePythonRequirement, PackagePythonRequirementEntity>>(),
            "package-python-requirement",
            PackagePythonRequirement.serializer()
        )
    }

    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, PythonVersion>>(),
            get<DtoValidator<PythonVersion>>(),
            "python-version",
            PythonVersion.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, PythonVersionSpecifier>>(),
            get<DtoValidator<PythonVersionSpecifier>>(),
            "python-version-specifier",
            PythonVersionSpecifier.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Version>>(),
            get<DtoValidator<Version>>(),
            "version",
            Version.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, VersionOverflow>>(),
            get<DtoValidator<VersionOverflow>>(),
            "version-overflow",
            VersionOverflow.serializer()
        )
    }
}
val runsApiModule = module {
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TestOutcomeActual>>(), get<DtoValidator<TestOutcomeActual>>(), "test-result", TestOutcomeActual.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, WorkflowRun>>(), get<DtoValidator<WorkflowRun>>(), "workflow-run", WorkflowRun.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, StepTestRun>>(), get<DtoValidator<StepTestRun>>(), "step-run", StepTestRun.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TestInput>>(), get<DtoValidator<TestInput>>(), "test-input", TestInput.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TestCase>>(), get<DtoValidator<TestCase>>(), "test-case", TestCase.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TestOutcomeExpectation>>(), get<DtoValidator<TestOutcomeExpectation>>(), "test-expectation", TestOutcomeExpectation.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, BenchmarkReport>>(), get<DtoValidator<BenchmarkReport>>(), "benchmark-report", BenchmarkReport.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, ExecutionNotification>>(), get<DtoValidator<ExecutionNotification>>(), "log-line", ExecutionNotification.serializer()
        )
    }
}
val stepsApiModule = module {
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Step>>(), get<DtoValidator<Step>>(), "step", Step.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, DataPointWrite>>(), get<DtoValidator<DataPointWrite>>(), "data-point-write", DataPointWrite.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, DataPointWrite>>(), get<DtoValidator<DataPointWrite>>(), "revision", DataPointWrite.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, DataPointRead>>(), get<DtoValidator<DataPointRead>>(), "provision", DataPointRead.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, DataPointInjection>>(), get<DtoValidator<DataPointInjection>>(), "prereq-injection", DataPointInjection.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Inquisition>>(), get<DtoValidator<Inquisition>>(), "inquisition", Inquisition.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, ErrorWatchFn>>(), get<DtoValidator<ErrorWatchFn>>(), "error-watch-fn", ErrorWatchFn.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, ErrorWatch>>(), get<DtoValidator<ErrorWatch>>(), "error-watch", ErrorWatch.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, WorkflowJunction>>(), get<DtoValidator<WorkflowJunction>>(), "workflow-junction", WorkflowJunction.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, ToolParam>>(), get<DtoValidator<ToolParam>>(), "tool-param", ToolParam.serializer()
        )
    }
}
val tagsApiModule = module {
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID,  TagRelation>>(),
            get<DtoValidator<TagRelation>>(),
            get<CompositeIdDaoDelegate<TagRelation, TagRelationEntity>>(),
            "tag-relation",
            TagRelation.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Tag>>(), get<DtoValidator<Tag>>(), "tag", Tag.serializer()
        )
    }
}
val transformApiModule = module {
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID,  TransformTargetType>>(),
            get<DtoValidator<TransformTargetType>>(),
            get<CompositeIdDaoDelegate<TransformTargetType, TransformTargetTypeEntity>>(),
            "transform-target",
            TransformTargetType.serializer()
        )
    }

    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TransformOccurrenceArg>>(), get<DtoValidator<TransformOccurrenceArg>>(), "applied-transform-arg", TransformOccurrenceArg.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TransformOccurrence>>(), get<DtoValidator<TransformOccurrence>>(), "applied-transform", TransformOccurrence.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Transform>>(), get<DtoValidator<Transform>>(), "transformation", Transform.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TransformArg>>(), get<DtoValidator<TransformArg>>(), "transformation-arg", TransformArg.serializer()
        )
    }
}
val typingApiModule = module {
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID,  TypeVarVariance>>(),
            get<DtoValidator<TypeVarVariance>>(),
            get<CompositeIdDaoDelegate<TypeVarVariance, TypeVarVarianceEntity>>(),
            "typevar-variance",
            TypeVarVariance.serializer()
        )
    }
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID, TypeUnionMember>>(),
            get<DtoValidator<TypeUnionMember>>(),
            get<CompositeIdDaoDelegate<TypeUnionMember, TypeUnionMemberEntity>>(),
            "union-member",
            TypeUnionMember.serializer()
        )
    }
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID,  TypeVarConstraint>>(),
            get<DtoValidator<TypeVarConstraint>>(),
            get<CompositeIdDaoDelegate<TypeVarConstraint, TypeVarConstraintEntity>>(),
            "typevar-constraint",
            TypeVarConstraint.serializer()
        )
    }
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID,  TypeVarBound>>(),
            get<DtoValidator<TypeVarBound>>(),
            get<CompositeIdDaoDelegate<TypeVarBound, TypeVarBoundEntity>>(),
            "typevar-bound",
            TypeVarBound.serializer()
        )
    }
    single<KtorApi<CompositeID>> {
        CompositeIdEndpointImpl(
            get<DLL<CompositeID,  TypeUnionMember>>(),
            get<DtoValidator<TypeUnionMember>>(),
            get<CompositeIdDaoDelegate<TypeUnionMember, TypeUnionMemberEntity>>(),
            "union-member",
            TypeUnionMember.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, CallableParameter>>(), get<DtoValidator<CallableParameter>>(), "callable-parameter", CallableParameter.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TypeSchemaRef>>(), get<DtoValidator<TypeSchemaRef>>(), "schema-ref", TypeSchemaRef.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TypeParameter>>(), get<DtoValidator<TypeParameter>>(), "type-parameter", TypeParameter.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, TypeNode>>(), get<DtoValidator<TypeNode>>(), "type-node", TypeNode.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, CallableSignature>>(), get<DtoValidator<CallableSignature>>(), "callable-signature", CallableSignature.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, ClassInheritance>>(), get<DtoValidator<ClassInheritance>>(), "class-inheritance", ClassInheritance.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, CallableParameter>>(), get<DtoValidator<CallableParameter>>(), "callable-parameter", CallableParameter.serializer()
        )
    }
}
val userApiModule = module {
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, User>>(), get<DtoValidator<User>>(), "user", User.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, PaymentMethod>>(), get<DtoValidator<PaymentMethod>>(), "payment-method", PaymentMethod.serializer()
        )
    }
}
val workflowApiModule = module {
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Step>>(), get<DtoValidator<Step>>(), "step", Step.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, WorkflowFolder>>(), get<DtoValidator<WorkflowFolder>>(), "workflow-folder", WorkflowFolder.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Tool>>(), get<DtoValidator<Tool>>(), "tool", Tool.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, Grouper>>(), get<DtoValidator<Grouper>>(), "phase", Grouper.serializer()
        )
    }
    single<KtorApi<ULong>> {
        ULongIdEndpointImpl(
            get<DLL<ULong, DataPoint>>(), get<DtoValidator<DataPoint>>(), "datapoint", DataPoint.serializer()
        )
    }
}