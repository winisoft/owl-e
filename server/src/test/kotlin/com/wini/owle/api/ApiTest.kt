package com.wini.owle.api

import com.wini.owle.api.environs.PyPyPackageEntityApiTest
import com.wini.owle.api.environs.*
import com.wini.owle.api.runs.*
import com.wini.owle.api.steps.*
import com.wini.owle.api.transforms.*
import com.wini.owle.api.typing.*
import com.wini.owle.api.user.*
import com.wini.owle.api.workflows.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("API Tests")
open class  ApiTest {
    
    @Nested
    @DisplayName("Environs Module")
    inner class EnvironsModuleTests {
        @Nested inner class PyPyPackageEntityApiTests: PyPyPackageEntityApiTest()
        @Nested inner class PyPyPackageEntityRepoApiTests: PyPyPackageEntityRepoApiTest()
        @Nested inner class ToolPyPackDepEntityApiTests: ToolPyPackDepEntityApiTest()
        @Nested inner class VersionSpecApiTests: VersionSpecApiTest()
    }

    @Nested
    @DisplayName("Runs Module")
    inner class RunsModuleTests {
        @Nested inner class BenchmarkReportApiTests: BenchmarkReportApiTest()
        @Nested inner class StepRunLogLineApiTests: StepRunLogLineApiTest()
        @Nested inner class ProvisionDataPointEntityApiTests: ProvisionDataPointEntityApiTest()
        @Nested inner class StepRunApiTests: StepRunApiTest()
        @Nested inner class TestCaseEntityApiTests: TestCaseApiTestEntity()
        @Nested inner class TestExpectationApiTests: TestExpectedOutcomeApiTest()
        @Nested inner class TestInputEntityApiTests: TestInputApiTestEntity()
        @Nested inner class WorkflowWorkflowTestResultApiTests: TestResultApiWorkflowWorkflowTest()
        @Nested inner class WorkflowExecutionApiTests: WorkflowExecutionApiTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class StepsModuleTests {
        @Nested inner class ErrorWatchEntityApiTests: ErrorWatchEntityApiTest()
        @Nested inner class ErrorWatchEntityFnApiTests: ErrorWatchEntityFnApiTest()
        @Nested inner class InquisitionEntityApiTests: InquisitionEntityApiTest()
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
        @Nested inner class AppliedTransformEntityEntityApiTests: AppliedTransformEntityEntityApiTest()
        @Nested inner class AppliedTransformEntityEntityArgApiTests: AppliedTransformEntityEntityArgApiTest()
        @Nested inner class TransformationApiTests: TransformationApiTest()
        @Nested inner class TransformationArgApiTests: TransformationArgApiTest()
        @Nested inner class TransformEntityTargetTypeApiTests: TransformEntityTargetTypeApiTest()
    }

    @Nested
    @DisplayName("Typing Module")
    inner class TypingModuleTests {
        @Nested inner class TypeBoundsApiTestsEntity: TypeBoundsApiTestEntity()
        @Nested inner class TypeDeclarationEntityApiTests: TypeDeclarationEntityApiTest()
        @Nested inner class TypeDefinitionEntityApiTests: TypeDefinitionEntityApiTest()
        @Nested inner class TypeParameterApiTests: TypeParameterApiTest()
        @Nested inner class TypeSchemaRefApiTests: TypeSchemaRefApiTest()
        @Nested inner class TypeUnionMemberApiTests: TypeUnionMemberApiTest()
        @Nested inner class TypeVarianceApiTests: TypeVarianceApiTest()
    }

    @Nested
    @DisplayName("User Module")
    inner class UserEntityModuleTests {
        @Nested
        inner class PaymentMethodEntityApiTests: PaymentMethodEntityApiTest()
        @Nested inner class TagEntityApiTests: TagEntityApiTest()
        @Nested inner class TagEntityRelationApiTests: TagEntityRelationApiTest()
        @Nested inner class UserEntityApiTests: UserEntityApiTest()
    }

    @Nested
    @DisplayName("Workflows Module")
    inner class WorkflowsModuleTests {
        @Nested inner class DataPointsApiTestsEntity: DataPointsApiTestEntity()
        @Nested inner class PhaseApiTests: PhaseApiTest()
        @Nested inner class WorkflowApiTests: WorkflowApiTest()
        @Nested inner class WorkflowFolderApiTests: WorkflowFolderApiTest()
    }
    
}