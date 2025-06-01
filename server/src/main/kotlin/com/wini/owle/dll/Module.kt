package com.wini.owle.dll

import com.wini.owle.dto.BaseDto
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
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.runs.workflow.WorkflowRun
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.*
import com.wini.owle.services.Service
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.koin.core.module.Module
import org.koin.dsl.module




val environsDllsModule = module {
    bindGenericDll<ULong, EnvMarkerComparison>()
    bindGenericDll<ULong, EnvMarkerExpression>()
    bindGenericDll<ULong, EnvMarkerLogical>()

    bindGenericDll<String, Distribution>()
    bindGenericDll<CompositeID, DistributionPackage>()
    bindGenericDll<ULong, PackageDistRequirement>()
    bindGenericDll<CompositeID, PackagePythonRequirement>()

    bindGenericDll<ULong, PythonVersion>()
    bindGenericDll<ULong, PythonVersionSpecifier>()
    bindGenericDll<ULong, Version>()
    bindGenericDll<ULong, VersionOverflow>()
}
val runsDllsModule = module {
    bindGenericDll<ULong, BenchmarkReport>()
    bindGenericDll<ULong, TestCase>()
    bindGenericDll<ULong, TestInput>()
    bindGenericDll<ULong, TestOutcomeExpectation>()
    bindGenericDll<ULong, TestOutcomeActual>()
    bindGenericDll<ULong, ExecutionNotification>()
    bindGenericDll<ULong, StepTestRun>()
    bindGenericDll<ULong, WorkflowRun>()
}
val stepDllsModule = module {
    bindGenericDll<ULong, ErrorWatchFn>()
    bindGenericDll<ULong, ErrorWatch>()
    bindGenericDll<ULong, Inquisition>()
    bindGenericDll<ULong, WorkflowJunction>()
    bindGenericDll<ULong, DataPointWrite>()
}
val tagDllsModule = module {
    bindGenericDll<CompositeID, TagRelation>()
}
val transformDllsModule = module {
    bindGenericDll<ULong, TransformOccurrenceArg>()
    bindGenericDll<ULong, TransformOccurrence>()
    bindGenericDll<ULong, TransformArg>()
    bindGenericDll<ULong, Transform>()
}
val typingDllsModule = module {
    bindGenericDll<ULong, TypeNode>()
    bindGenericDll<ULong, TypeParameter>()
    bindGenericDll<ULong, TypeSchemaRef>()
    bindGenericDll<CompositeID, TypeVarVariance>()
    bindGenericDll<CompositeID, TransformTargetType>()
    bindGenericDll<CompositeID, TypeUnionMember>()
}
val userDllsModule = module {
    bindGenericDll<ULong, PaymentMethod>()
}
val workflowDllsModule = module {
    bindGenericDll<ULong, Tag>()
    bindGenericDll<ULong, User>()
    bindGenericDll<ULong, Phase>()
    bindGenericDll<ULong, Step>()
    bindGenericDll<ULong, ToolParam>()
    bindGenericDll<ULong, Tool>()
    bindGenericDll<ULong, WorkflowFolder>()
    bindGenericDll<ULong, Workflow>()
}



inline fun <
        reified ID : Any,
        reified DTO : BaseDto
        > Module.bindGenericDll() {
    single<GenericDllImpl<ID, DTO>> {
        GenericDllImpl(service = get<Service<ID, DTO>>())
    }
    single<DLL<ID, DTO>> { get<GenericDllImpl<ID, DTO>>() }
}