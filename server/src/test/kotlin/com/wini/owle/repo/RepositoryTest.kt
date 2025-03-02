package com.wini.owle.repo

import com.wini.owle.repo.environs.*
import com.wini.owle.repo.runs.*
import com.wini.owle.repo.steps.*
import com.wini.owle.repo.transforms.*
import com.wini.owle.repo.typing.*
import com.wini.owle.repo.user.*
import com.wini.owle.repo.workflows.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


@DisplayName("Repository Tests")
open class RepositoryTest {

    @Nested
    @DisplayName("Environs Module")
    inner class RepositoryEnvironsModuleTest {

        @Nested
        @DisplayName("PackageRepoRepository")
        inner class PackageRepoTest: PackageRepoRepositoryTest()

        @Nested
        @DisplayName("PackageRepository")
        inner class PackageTest: PackageRepositoryTest()

        @Nested
        @DisplayName("VersionSpecRepository")
        inner class VersionSpecTest: VersionSpecRepositoryTest()
        
    }

    @Nested
    @DisplayName("Runs Module")
    inner class RepositoryRunsModuleTest {

        @Nested
        @DisplayName("BenchmarkingDataRepository")
        inner class BenchmarkingDataTest: BenchmarkingDataRepositoryTest()

        @Nested
        @DisplayName("ExecutionLogRepository")
        inner class ExecutionLogTest: ExecutionLogRepositoryTest()

        @Nested
        @DisplayName("StepBenchmarkingDataRepository")
        inner class StepBenchmarkingTest: StepBenchmarkingDatasRepositoryTest()

        @Nested
        @DisplayName("StepExecutionRepository")
        inner class StepExecutionTest: StepExecutionRepositoryTest()

        @Nested
        @DisplayName("TestCaseRepository")
        inner class TestCaseTest: TestCaseRepositoryTest()

        @Nested
        @DisplayName("TestExpectedOutcomeRepository")
        inner class TestExpectedOutcomeTest: TestExpectedOutcomeRepositoryTest()

        @Nested
        @DisplayName("TestInputRepository")
        inner class TestInputTest: TestInputRepositoryTest()

        @Nested
        @DisplayName("TestResultRepository")
        inner class TestResultTest: TestResultRepositoryTest()

        @Nested
        @DisplayName("WorkflowExecutionRepository")
        inner class WorkflowExecutionTest: WorkflowExecutionRepositoryTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class RepositoryStepsModuleTest {

        @Nested
        @DisplayName("ErrorWatchRepository")
        inner class ErrorWatchTest: ErrorWatchRepositoryTest()

        @Nested
        @DisplayName("ErrorWatchFnRepository")
        inner class ErrorWatchFnTest: ErrorWatchFnRepositoryTest()

        @Nested
        @DisplayName("InquisitionRepository")
        inner class InquisitionTest: InquisitionRepositoryTest()

        @Nested
        @DisplayName("PreReqInjectionRepository")
        inner class PreReqInjectionTest: PreReqInjectionRepositoryTest()

        @Nested
        @DisplayName("ProvisionRepository")
        inner class ProvisionTest: ProvisionRepositoryTest()

        @Nested
        @DisplayName("RevisionRepository")
        inner class RevisionTest: RevisionRepositoryTest()

        @Nested
        @DisplayName("WorkflowJunctionRepository")
        inner class WorkflowJunctionTest: WorkflowJunctionRepositoryTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class RepositoryTransformsModuleTest {

        @Nested
        @DisplayName("AppliedTransformArgRepository")
        inner class AppliedTransformArgTest: AppliedTransformArgRepositoryTest()

        @Nested
        @DisplayName("AppliedTransformRepository")
        inner class AppliedTransformTest: AppliedTransformRepositoryTest()

        @Nested
        @DisplayName("TransformationArgRepository")
        inner class TransformationArgTest: TransformationArgRepositoryTest()

        @Nested
        @DisplayName("TransformationRepository")
        inner class TransformationTest: TransformationRepositoryTest()

        @Nested
        @DisplayName("TransformArgTargetTypeRepository")
        inner class TransformationTargetTypeTest: TransformArgTargetTypeRepositoryTest()
        
    }

    @Nested
    @DisplayName("Typing Module")
    inner class RepositoryTypingModuleTests {

        @Nested
        @DisplayName("TypeBoundRepository")
        inner class TypeBoundTest: TypeBoundRepositoryTest()

        @Nested
        @DisplayName("TypeDeclarationRepository")
        inner class TypeDeclarationTest: TypeDeclarationRepositoryTest()

        @Nested
        @DisplayName("TypeDefinitionRepository")
        inner class TypeDefinitionTest: TypeDefinitionRepositoryTest()

        @Nested
        @DisplayName("TypeParameterRepository")
        inner class TypeParameterTest: TypeParameterRepositoryTest()

        @Nested
        @DisplayName("TypeSchemaRefRepository")
        inner class TypeSchemaRefTest: TypeSchemaRefRepositoryTest()

        @Nested
        @DisplayName("TypeVarianceRepository")
        inner class TypeVariancesTest: TypeVariancesRepositoryTest()
        
    }

    @Nested
    @DisplayName("User Module")
    inner class RepositoryUserModuleTest {

        @Nested
        @DisplayName("PaymentMethodRepository")
        inner class PaymentMethodTest: PaymentMethodRepositoryTest()
        
        @Nested
        @DisplayName("TagRepository")
        inner class TagTest: TagRepositoryTest()

        @Nested
        @DisplayName("UserRepository")
        inner class UserTest: UserRepositoryTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class RepositoryWorkflowsModuleTest {

        @Nested
        @DisplayName("DataPointRepository")
        inner class DataPointTest: DataPointRepositoryTest()

        @Nested
        @DisplayName("PhaseRepository")
        inner class PhaseTest: PhaseRepositoryTest()

        @Nested
        @DisplayName("StepRepository")
        inner class StepTest: StepRepositoryTest()

        @Nested
        @DisplayName("ToolParamRepository")
        inner class ToolParamTest: ToolParamRepositoryTest()

        @Nested
        @DisplayName("ToolRepository")
        inner class ToolTest: ToolRepositoryTest()

        @Nested
        @DisplayName("WorkflowFolderRepository")
        inner class WorkflowFolderTest: WorkflowFolderRepositoryTest()

        @Nested
        @DisplayName("WorkflowRepository")
        inner class WorkflowTest: WorkflowRepositoryTest()
        
    }
}