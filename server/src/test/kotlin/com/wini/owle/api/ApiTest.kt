package com.wini.owle.api

import com.wini.owle.api.environs.PackageApiTest
import com.wini.owle.api.environs.*
import com.wini.owle.api.runs.*
import com.wini.owle.api.steps.*
import com.wini.owle.api.transforms.*
import com.wini.owle.api.typing.*
import com.wini.owle.api.user.*
import com.wini.owle.api.workflows.*
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("API Tests")
open class  ApiTest {
    
    @Nested
    @DisplayName("Environs Module")
    inner class EnvironsModuleTests {
        @Nested inner class PackageApiTests: PackageApiTest()
        @Nested inner class PackageRepoApiTests: PackageRepoApiTest()
        @Nested inner class ToolPackageDependencyApiTests: ToolPackageDependencyApiTest()
        @Nested inner class VersionSpecApiTests: VersionSpecApiTest()
    }

    @Nested
    @DisplayName("Runs Module")
    inner class RunsModuleTests {
        @Nested inner class BenchmarkingDataApiTests: BenchmarkingDataApiTest()
        @Nested inner class ExecutionLogApiTests: ExecutionLogApiTest()
        @Nested inner class ProvisionDataPointApiTests: ProvisionDataPointApiTest()
        @Nested inner class StepExecutionApiTests: StepExecutionApiTest()
        @Nested inner class TestCaseApiTests: TestCaseApiTest()
        @Nested inner class TestExpectedOutcomeApiTests: TestExpectedOutcomeApiTest()
        @Nested inner class TestInputApiTests: TestInputApiTest()
        @Nested inner class TestResultApiTests: TestResultApiTest()
        @Nested inner class WorkflowExecutionApiTests: WorkflowExecutionApiTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class StepsModuleTests {
        @Nested inner class ErrorWatchApiTests: ErrorWatchApiTest()
        @Nested inner class ErrorWatchFnApiTests: ErrorWatchFnApiTest()
        @Nested inner class InquisitionApiTests: InquisitionApiTest()
        @Nested inner class PreReqInjectionApiTests: PreReqInjectionApiTest()
        @Nested inner class ProvisionApiTests: ProvisionApiTest()
        @Nested inner class RevisionApiTests: RevisionApiTest()
        @Nested inner class StepApiTests: StepApiTest()
        @Nested inner class ToolApiTests: ToolApiTest()
        @Nested inner class ToolParamApiTests: ToolParamApiTest()
        @Nested inner class WorkflowJunctionApiTests: WorkflowJunctionApiTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class TransformsModuleTests {
        @Nested inner class AppliedTransformApiTests: AppliedTransformApiTest()
        @Nested inner class AppliedTransformArgApiTests: AppliedTransformArgApiTest()
        @Nested inner class TransformationApiTests: TransformationApiTest()
        @Nested inner class TransformationArgApiTests: TransformationArgApiTest()
        @Nested inner class TransformTargetTypeApiTests: TransformTargetTypeApiTest()
    }

    @Nested
    @DisplayName("Typing Module")
    inner class TypingModuleTests {
        @Nested inner class TypeBoundsApiTests: TypeBoundsApiTest()
        @Nested inner class TypeDeclarationApiTests: TypeDeclarationApiTest()
        @Nested inner class TypeDefinitionApiTests: TypeDefinitionApiTest()
        @Nested inner class TypeParameterApiTests: TypeParameterApiTest()
        @Nested inner class TypeSchemaRefApiTests: TypeSchemaRefApiTest()
        @Nested inner class TypeUnionMemberApiTests: TypeUnionMemberApiTest()
        @Nested inner class TypeVarianceApiTests: TypeVarianceApiTest()
    }

    @Nested
    @DisplayName("User Module")
    inner class UserModuleTests {
        @Nested
        inner class PaymentMethodApiTests: PaymentMethodApiTest()
        @Nested inner class TagApiTests: TagApiTest()
        @Nested inner class TagRelationApiTests: TagRelationApiTest()
        @Nested inner class UserApiTests: UserApiTest()
    }

    @Nested
    @DisplayName("Workflows Module")
    inner class WorkflowsModuleTests {
        @Nested inner class DataPointsApiTests: DataPointsApiTest()
        @Nested inner class PhaseApiTests: PhaseApiTest()
        @Nested inner class WorkflowApiTests: WorkflowApiTest()
        @Nested inner class WorkflowFolderApiTests: WorkflowFolderApiTest()
    }
    
}