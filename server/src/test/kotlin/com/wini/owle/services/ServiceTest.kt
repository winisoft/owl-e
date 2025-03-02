package com.wini.owle.services

import com.wini.owle.services.environs.PackageRepoServiceTest
import com.wini.owle.services.environs.PackageServiceTest
import com.wini.owle.services.environs.VersionSpecServiceTest
import com.wini.owle.services.runs.*
import com.wini.owle.services.steps.*
import com.wini.owle.services.transforms.*
import com.wini.owle.services.typing.*
import com.wini.owle.services.user.PaymentMethodServiceTest
import com.wini.owle.services.user.TagServiceTest
import com.wini.owle.services.user.UserServiceTest
import com.wini.owle.services.workflows.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("Repository Tests")
open class ServiceTest {

    @Nested
    @DisplayName("Environs Module")
    inner class ServiceEnvironsModuleTest {

        @Nested
        @DisplayName("PackageRepoService")
        inner class PackageRepoTest: PackageRepoServiceTest()

        @Nested
        @DisplayName("PackageService")
        inner class PackageTest: PackageServiceTest()

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
        @DisplayName("StepBenchmarkingDataService")
        inner class StepBenchmarkingTest: StepBenchmarkingDataServiceTest()

        @Nested
        @DisplayName("StepExecutionService")
        inner class StepExecutionTest: StepExecutionServiceTest()

        @Nested
        @DisplayName("TestCaseService")
        inner class TestCaseTest: TestCaseServiceTest()

        @Nested
        @DisplayName("TestExpectedOutcomeService")
        inner class TestExpectedOutcomeTest: TestExpectedOutcomeServiceTest()

        @Nested
        @DisplayName("TestInputService")
        inner class TestInputTest: TestInputServiceTest()

        @Nested
        @DisplayName("TestResultService")
        inner class TestResultTest: TestResultServiceTest()

        @Nested
        @DisplayName("WorkflowExecutionService")
        inner class WorkflowExecutionTest: WorkflowExecutionServiceTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class ServiceStepsModuleTest {

        @Nested
        @DisplayName("ErrorWatchService")
        inner class ErrorWatchTest: ErrorWatchServiceTest()

        @Nested
        @DisplayName("ErrorWatchFnService")
        inner class ErrorWatchFnTest: ErrorWatchFnServiceTest()

        @Nested
        @DisplayName("InquisitionService")
        inner class InquisitionTest: InquisitionServiceTest()

        @Nested
        @DisplayName("PreReqInjectionService")
        inner class PreReqInjectionTest: PreReqInjectionServiceTest()

        @Nested
        @DisplayName("ProvisionService")
        inner class ProvisionTest: ProvisionServiceTest()

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
        inner class AppliedTransformArgTest: AppliedTransformArgServiceTest()

        @Nested
        @DisplayName("AppliedTransformService")
        inner class AppliedTransformTest: AppliedTransformServiceTest()

        @Nested
        @DisplayName("TransformationArgService")
        inner class TransformationArgTest: TransformationArgServiceTest()

        @Nested
        @DisplayName("TransformationService")
        inner class TransformationTest: TransformationServiceTest()

        @Nested
        @DisplayName("TransformArgTargetTypeService")
        inner class TransformationTargetTypeTest: TransformArgTargetTypeServiceTest()

    }

    @Nested
    @DisplayName("Typing Module")
    inner class ServiceTypingModuleTests {

        @Nested
        @DisplayName("TypeBoundService")
        inner class TypeBoundTest: TypeBoundServiceTest()

        @Nested
        @DisplayName("TypeDeclarationService")
        inner class TypeDeclarationTest: TypeDeclarationServiceTest()

        @Nested
        @DisplayName("TypeDefinitionService")
        inner class TypeDefinitionTest: TypeDefinitionServiceTest()

        @Nested
        @DisplayName("TypeParameterService")
        inner class TypeParameterTest: TypeParameterServiceTest()

        @Nested
        @DisplayName("TypeSchemaRefService")
        inner class TypeSchemaRefTest: TypeSchemaRefServiceTest()

        @Nested
        @DisplayName("TypeVarianceService")
        inner class TypeVariancesTest: TypeVarianceServiceTest()

    }

    @Nested
    @DisplayName("User Module")
    inner class ServiceUserModuleTest {

        @Nested
        @DisplayName("PaymentMethodService")
        inner class PaymentMethodTest: PaymentMethodServiceTest()

        @Nested
        @DisplayName("TagService")
        inner class TagTest: TagServiceTest()

        @Nested
        @DisplayName("UserService")
        inner class UserTest: UserServiceTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class ServiceWorkflowsModuleTest {

        @Nested
        @DisplayName("DataPointService")
        inner class DataPointTest: DataPointServiceTest()

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