package com.wini.owle.serialization

import com.wini.owle.dto.*
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.PaymentMethod
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.dto.user.tags.TagRelation
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val polymorphicJson = Json {
    serializersModule = SerializersModule {
        polymorphic(BaseDto::class) {
            subclass(PyPackageRepo::class, PyPackageRepo.serializer())
            subclass(VersionSpec::class, VersionSpec.serializer())
            subclass(Step::class, Step.serializer())
            subclass(Workflow::class, Workflow.serializer())
            subclass(TestCase::class, TestCase.serializer())
            subclass(TestInput::class, TestInput.serializer())
            subclass(TestExpectation::class, TestExpectation.serializer())
            subclass(WorkflowTestResult::class, WorkflowTestResult.serializer())
            subclass(BenchmarkReport::class, BenchmarkReport.serializer())
            subclass(StepRunLogLine::class, StepRunLogLine.serializer())
            subclass(ErrorWatch::class, ErrorWatch.serializer())
            subclass(ErrorWatchFn::class, ErrorWatchFn.serializer())
            subclass(Inquisition::class, Inquisition.serializer())
            subclass(InitialDataPointInjector::class, InitialDataPointInjector.serializer())
            subclass(Provision::class, Provision.serializer())
            subclass(DataPointSave::class, DataPointSave.serializer())
            subclass(TypeDeclaration::class, TypeDeclaration.serializer())
            subclass(TypeDefinition::class, TypeDefinition.serializer())
            subclass(TypeParameter::class, TypeParameter.serializer())
            subclass(TypeSchemaRef::class, TypeSchemaRef.serializer())
            subclass(TypeUnionMember::class, TypeUnionMember.serializer())
            subclass(TypeVariance::class, TypeVariance.serializer())
            subclass(User::class, User.serializer())
            subclass(PaymentMethod::class, PaymentMethod.serializer())
            subclass(Tag::class, Tag.serializer())
            subclass(TagRelation::class, TagRelation.serializer())
            subclass(DataPoint::class, DataPoint.serializer())
            subclass(Phase::class, Phase.serializer())
            subclass(ProvisionDataPoint::class, ProvisionDataPoint.serializer())
            subclass(Tool::class, Tool.serializer())
            subclass(ToolParam::class, ToolParam.serializer())
            subclass(WorkflowFolder::class, WorkflowFolder.serializer())
        }
    }
    encodeDefaults = true
    prettyPrint = true
}
