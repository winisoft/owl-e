package com.wini.owle.services

import com.wini.owle.services.environs.PyPackageRepoServiceTest
import com.wini.owle.services.environs.PyPackageServiceTest
import com.wini.owle.services.environs.ToolPyPackageDependencyServiceTest
import com.wini.owle.services.environs.VersionSpecServiceTest
import com.wini.owle.services.runs.*
import com.wini.owle.services.steps.*
import com.wini.owle.services.transforms.*
import com.wini.owle.services.typing.*
import com.wini.owle.services.user.PaymentMethodEntityServiceTest
import com.wini.owle.services.user.TagEntityRelationServiceTest
import com.wini.owle.services.user.TagEntityServiceTest
import com.wini.owle.services.user.UserEntityServiceTest
import com.wini.owle.services.workflows.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("Service Layer Tests")
open class ServiceTest {

    @Nested
    @DisplayName("Environs Module")
    inner class ServiceEnvironsModuleTest {

        @Nested
        @DisplayName("PackageRepoService")
        inner class PyPackageRepoTest: PyPackageRepoServiceTest()

        @Nested
        @DisplayName("PackageService")
        inner class PyPackageTest: PyPackageServiceTest()

        @Nested
        @DisplayName("ToolPackageDependency")
        inner class ToolPyPackageDepServiceTest: ToolPyPackageDependencyServiceTest()

        @Nested
        @DisplayName("VersionSpecService")
        inner class VersionSpecTest: VersionSpecServiceTest()

    }

    @Nested
    @DisplayName("Runs Module")
    inner class ServiceRunsModuleTest {

        @Nested
        @DisplayName("BenchmarkingDataService")
        inner class BenchmarkingDataTest: BenchmarkingDataServiceTest()

        @Nested
        @DisplayName("ExecutionLogService")
        inner class ExecutionLogTest: ExecutionLogServiceTest()

        @Nested
        @DisplayName("StepExecutionService")
        inner class StepExecutionTest: StepExecutionServiceTest()

        @Nested
        @DisplayName("TestCaseService")
        inner class TestCaseTestEntity: TestCaseServiceTestEntity()

        @Nested
        @DisplayName("TestExpectedOutcomeService")
        inner class TestExpectedOutcomeTest: TestExpectedOutcomeServiceTest()

        @Nested
        @DisplayName("TestInputService")
        inner class TestInputTestEntity: TestInputServiceTestEntity()

        @Nested
        @DisplayName("TestResultService")
        inner class TestResultWorkflowTest: TestResultServiceWorkflowTest()

        @Nested
        @DisplayName("WorkflowExecutionService")
        inner class WorkflowExecutionTest: WorkflowExecutionServiceTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class ServiceStepsModuleTest {

        @Nested
        @DisplayName("ErrorWatchService")
        inner class ErrorWatchEntityTest: ErrorWatchEntityServiceTest()

        @Nested
        @DisplayName("ErrorWatchFnService")
        inner class ErrorWatchEntityFnTest: ErrorWatchEntityFnServiceTest()

        @Nested
        @DisplayName("InquisitionService")
        inner class InquisitionEntityTest: InquisitionEntityServiceTest()

        @Nested
        @DisplayName("PreReqInjectionService")
        inner class PreReqInjectionTest: PreReqInjectionServiceTest()

        @Nested
        @DisplayName("ProvisionService")
        inner class ProvisionTest: ProvisionServiceTest()

        @Nested
        @DisplayName("ProvisionDataPointService")
        inner class ProvisionDataPointEntityTest: ProvisionDataPointEntityServiceTest()

        @Nested
        @DisplayName("RevisionService")
        inner class RevisionTest: RevisionServiceTest()

        @Nested
        @DisplayName("WorkflowJunctionService")
        inner class WorkflowJunctionTest: WorkflowJunctionServiceTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class ServiceTransformsModuleTest {

        @Nested
        @DisplayName("AppliedTransformArgService")
        inner class AppliedTransformEntityEntityArgTest: AppliedTransformEntityEntityArgServiceTest()

        @Nested
        @DisplayName("AppliedTransformService")
        inner class AppliedTransformEntityEntityTest: AppliedTransformEntityEntityServiceTest()

        @Nested
        @DisplayName("TransformationArgService")
        inner class TransformationArgTest: TransformationArgServiceTest()

        @Nested
        @DisplayName("TransformationService")
        inner class TransformationTest: TransformationServiceTest()

        @Nested
        @DisplayName("TransformArgTargetTypeService")
        inner class TransformationTargetTypeTest: TransformEntityArgTargetTypeServiceTest()

    }

    @Nested
    @DisplayName("Typing Module")
    inner class ServiceTypingModuleTests {

        @Nested
        @DisplayName("TypeBoundService")
        inner class TypeBoundEntityTest: TypeBoundEntityServiceTest()

        @Nested
        @DisplayName("TypeDeclarationService")
        inner class TypeDeclarationEntityTest: TypeDeclarationEntityServiceTest()

        @Nested
        @DisplayName("TypeDefinitionService")
        inner class TypeDefinitionEntityTest: TypeDefinitionEntityServiceTest()

        @Nested
        @DisplayName("TypeParameterService")
        inner class TypeParameterTest: TypeParameterServiceTest()

        @Nested
        @DisplayName("TypeSchemaRefService")
        inner class TypeSchemaRefTest: TypeSchemaRefServiceTest()

        @Nested
        @DisplayName("TypeUnionMemberService")
        inner class TypeUnionMemberTest: TypeUnionMemberServiceTest()

        @Nested
        @DisplayName("TypeVarianceService")
        inner class TypeVariancesTest: TypeVarianceServiceTest()

    }

    @Nested
    @DisplayName("User Module")
    inner class ServiceUserEntityModuleTest {

        @Nested
        @DisplayName("PaymentMethodService")
        inner class PaymentMethodEntityTest: PaymentMethodEntityServiceTest()

        @Nested
        @DisplayName("TagRelationService")
        inner class TagEntityRelationTest: TagEntityRelationServiceTest()

        @Nested
        @DisplayName("TagService")
        inner class TagEntityTest: TagEntityServiceTest()

        @Nested
        @DisplayName("UserService")
        inner class UserEntityTest: UserEntityServiceTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class ServiceWorkflowsModuleTest {

        @Nested
        @DisplayName("DataPointService")
        inner class DataPointEntityTest: DataPointEntityServiceTest()

        @Nested
        @DisplayName("PhaseService")
        inner class PhaseTest: PhaseServiceTest()

        @Nested
        @DisplayName("StepService")
        inner class StepTest: StepServiceTest()

        @Nested
        @DisplayName("ToolParamService")
        inner class ToolParamTest: ToolParamServiceTest()

        @Nested
        @DisplayName("ToolService")
        inner class ToolTest: ToolServiceTest()

        @Nested
        @DisplayName("WorkflowFolderService")
        inner class WorkflowFolderTest: WorkflowFolderServiceTest()

        @Nested
        @DisplayName("WorkflowService")
        inner class WorkflowTest: WorkflowServiceTest()

    }
}