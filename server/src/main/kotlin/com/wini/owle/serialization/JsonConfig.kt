package com.wini.owle.serialization

import com.wini.owle.dto.*
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
import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val polymorphicJson = Json {
    serializersModule = SerializersModule {
        polymorphic(BaseDto::class) {
            subclass(PythonVersionSpecifier::class, PythonVersionSpecifier.serializer())
            subclass(PythonVersion::class, PythonVersion.serializer())
            subclass(EnvMarkerLogical::class, EnvMarkerLogical.serializer())
            subclass(VersionOverflow::class, VersionOverflow.serializer())
            subclass(EnvMarkerExpression::class, EnvMarkerExpression.serializer())
            subclass(Distribution::class, Distribution.serializer())
            subclass(DistributionPackage::class, DistributionPackage.serializer())
            subclass(Version::class, Version.serializer())
            subclass(PackageDistRequirement::class, PackageDistRequirement.serializer())
            subclass(PackagePythonRequirement::class, PackagePythonRequirement.serializer())
            subclass(EnvMarkerComparison::class, EnvMarkerComparison.serializer())
            subclass(Step::class, Step.serializer())
            subclass(Workflow::class, Workflow.serializer())
            subclass(TestCase::class, TestCase.serializer())
            subclass(TestInput::class, TestInput.serializer())
            subclass(TestOutcomeExpectation::class, TestOutcomeExpectation.serializer())
            subclass(TestOutcomeActual::class, TestOutcomeActual.serializer())
            subclass(BenchmarkReport::class, BenchmarkReport.serializer())
            subclass(ExecutionNotification::class, ExecutionNotification.serializer())
            subclass(ErrorWatch::class, ErrorWatch.serializer())
            subclass(ErrorWatchFn::class, ErrorWatchFn.serializer())
            subclass(Inquisition::class, Inquisition.serializer())
            subclass(DataPointInjection::class, DataPointInjection.serializer())
            subclass(DataPointRead::class, DataPointRead.serializer())
            subclass(DataPointWrite::class, DataPointWrite.serializer())
            subclass(TypeNode::class, TypeNode.serializer())
            subclass(TypeParameter::class, TypeParameter.serializer())
            subclass(TypeSchemaRef::class, TypeSchemaRef.serializer())
            subclass(TypeUnionMember::class, TypeUnionMember.serializer())
            subclass(TypeVarVariance::class, TypeVarVariance.serializer())
            subclass(User::class, User.serializer())
            subclass(PaymentMethod::class, PaymentMethod.serializer())
            subclass(Tag::class, Tag.serializer())
            subclass(TagRelation::class, TagRelation.serializer())
            subclass(DataPoint::class, DataPoint.serializer())
            subclass(Grouper::class, Grouper.serializer())
            subclass(Tool::class, Tool.serializer())
            subclass(ToolParam::class, ToolParam.serializer())
            subclass(WorkflowFolder::class, WorkflowFolder.serializer())
        }
    }
    encodeDefaults = true
    prettyPrint = true
}
