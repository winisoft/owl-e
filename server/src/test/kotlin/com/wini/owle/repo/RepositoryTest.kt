package com.wini.owle.repo

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


@DisplayName("Repository Tests")
open class RepositoryTest {

    @Nested
    @DisplayName("Environs Module")
    inner class RepositoryEnvironsModuleTest {

        @Nested
        @DisplayName("PackageRepoRepository")
        inner class PackageRepoRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("PackageRepository")
        inner class PackageRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("VersionSpecRepository")
        inner class VersionSpecRepositoryTests: OwleRepoImplTest()
        
    }

    @Nested
    @DisplayName("Runs Module")
    inner class RepositoryRunsModuleTest {

        @Nested
        @DisplayName("BenchmarkingDataRepository")
        inner class BenchmarkingDataRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("ExecutionLogRepository")
        inner class ExecutionLogRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("StepBenchmarkingDataRepository")
        inner class StepBenchmarkingDatasRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("StepExecutionRepository")
        inner class StepExecutionRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("TestCaseRepository")
        inner class TestCaseRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("TestExpectedOutcomeRepository")
        inner class TestExpectedOutcomeRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("TestInputRepository")
        inner class TestInputRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("TestResultRepository")
        inner class TestResultRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("WorkflowExecutionRepository")
        inner class WorkflowExecutionRepositoryTests: OwleRepoImplTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class RepositoryStepsModuleTest {

        @Nested
        @DisplayName("ErrorWatchRepository")
        inner class ErrorWatchRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("ErrorWatchFnRepository")
        inner class ErrorWatchFnRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("InquisitionRepository")
        inner class InquisitionRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("PreReqInjectionRepository")
        inner class PreReqInjectionRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("ProvisionRepository")
        inner class ProvisionRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("RevisionRepository")
        inner class RevisionRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("WorkflowJunctionRepository")
        inner class WorkflowJunctionRepositoryTests: OwleRepoImplTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class RepositoryTransformsModuleTest {

        @Nested
        @DisplayName("AppliedTransformArgRepository")
        inner class AppliedTransformArgRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("AppliedTransformRepository")
        inner class AppliedTransformRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("TransformationArgRepository")
        inner class TransformationArgRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("TransformationRepository")
        inner class TransformationRepositoryTest: OwleRepoImplTest()

//        @Nested
//        @DisplayName("TransformArgTargetTypeRepository")
//        inner class TransformationTargetTypeTest: OwleRepoImplTest()
        
    }

    @Nested
    @DisplayName("Typing Module")
    inner class RepositoryTypingModuleTests {

        @Nested
        @DisplayName("TypeBoundRepository")
        inner class TypeBoundRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("TypeDeclarationRepository")
        inner class TypeDeclarationRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("TypeDefinitionRepository")
        inner class TypeDefinitionRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("TypeParameterRepository")
        inner class TypeParameterRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("TypeSchemaRefRepository")
        inner class TypeSchemaRefRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("TypeVarianceRepository")
        inner class TypeVariancesRepositoryTest: OwleRepoImplTest()
        
    }

    @Nested
    @DisplayName("User Module")
    inner class RepositoryUserModuleTest {

        @Nested
        @DisplayName("PaymentMethodRepository")
        inner class PaymentMethodRepositoryTests: OwleRepoImplTest()
        
        @Nested
        @DisplayName("TagRepository")
        inner class TagRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("UserRepository")
        inner class UserRepositoryTest: OwleRepoImplTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class RepositoryWorkflowsModuleTest {

        @Nested
        @DisplayName("DataPointRepository")
        inner class DataPointRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("PhaseRepository")
        inner class PhaseRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("StepRepository")
        inner class StepRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("ToolParamRepository")
        inner class ToolParamRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("ToolRepository")
        inner class ToolRepositoryTest: OwleRepoImplTest()

        @Nested
        @DisplayName("WorkflowFolderRepository")
        inner class WorkflowFolderRepositoryTests: OwleRepoImplTest()

        @Nested
        @DisplayName("WorkflowRepository")
        inner class WorkflowRepositoryTest : OwleRepoImplTest()
        
    }
}