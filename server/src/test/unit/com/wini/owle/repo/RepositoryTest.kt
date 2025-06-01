package com.wini.owle.repo

import com.wini.owle.dto.DtoTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("Repository Tests")
open class RepositoryTest {

//
//    @Nested
//    @DisplayName("Environs Module")
//    inner class RepositoryEnvironsModuleTest {
//
//        @Nested
//        @DisplayName("PythonVersionRepository")
//        inner class PythonVersionRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("VersionSpecRepository")
//        inner class VersionSpecifierRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("DistributionRepository")
//        inner class DistributionRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("OverflowVersionComponentRepository")
//        inner class OverflowVersionComponentRepositoryTest: ULongIdImplTest()
//
//
//
//        @Nested
//        @DisplayName("SpecifierElementRepository")
//        inner class SpecifierElementRepositoryTest: ULongIdImplTest()
//
//    }
//
//    @Nested
//    @DisplayName("Runs Module")
//    inner class RepositoryRunsModuleTest {
//
//        @Nested
//        @DisplayName("BenchmarkingDataRepository")
//        inner class BenchmarkingDataRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("ExecutionLogRepository")
//        inner class ExecutionLogRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("StepBenchmarkingDataRepository")
//        inner class StepBenchmarkingDatasRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("StepExecutionRepository")
//        inner class StepExecutionRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TestCaseRepository")
//        inner class TestCaseRepositoryTestEntity: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TestExpectedOutcomeRepository")
//        inner class TestExpectedOutcomeRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TestInputRepository")
//        inner class TestInputRepositoryTestEntity: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TestResultRepository")
//        inner class TestResultRepositoryWorkflowTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("WorkflowExecutionRepository")
//        inner class WorkflowExecutionRepositoryTests: ULongIdImplTest()
//
//    }
//
//    @Nested
//    @DisplayName("Steps Module")
//    inner class RepositoryStepsModuleTest {
//
//        @Nested
//        @DisplayName("ErrorWatchRepository")
//        inner class ErrorWatchEntityRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("ErrorWatchFnRepository")
//        inner class ErrorWatchEntityFnRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("InquisitionRepository")
//        inner class InquisitionEntityRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("PreReqInjectionRepository")
//        inner class PreReqInjectionRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("ProvisionRepository")
//        inner class ProvisionRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("RevisionRepository")
//        inner class RevisionRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("WorkflowJunctionRepository")
//        inner class WorkflowJunctionRepositoryTests: ULongIdImplTest()
//
//    }
//
//    @Nested
//    @DisplayName("Transforms Module")
//    inner class RepositoryTransformsModuleTest {
//
//        @Nested
//        @DisplayName("AppliedTransformArgRepository")
//        inner class AppliedTransformEntityEntityArgRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("AppliedTransformRepository")
//        inner class AppliedTransformEntityEntityRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TransformationArgRepository")
//        inner class TransformationArgRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TransformationRepository")
//        inner class TransformationRepositoryTest: ULongIdImplTest()
//
////        @Nested
////        @DisplayName("TransformArgTargetTypeRepository")
////        inner class TransformationTargetTypeTest: OwleRepoImplTest()
//
//    }
//
//    @Nested
//    @DisplayName("Typing Module")
//    inner class RepositoryTypingModuleTests {
//
//        @Nested
//        @DisplayName("TypeBoundRepository")
//        inner class TypeBoundEntityRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TypeDeclarationRepository")
//        inner class TypeDeclarationEntityRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TypeDefinitionRepository")
//        inner class TypeDefinitionEntityRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TypeParameterRepository")
//        inner class TypeParameterRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TypeSchemaRefRepository")
//        inner class TypeSchemaRefRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TypeVarianceRepository")
//        inner class TypeVariancesRepositoryTest: ULongIdImplTest()
//
//    }
//
//    @Nested
//    @DisplayName("User Module")
//    inner class RepositoryUserEntityModuleTest {
//
//        @Nested
//        @DisplayName("PaymentMethodRepository")
//        inner class PaymentMethodEntityRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("TagRepository")
//        inner class TagEntityRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("UserRepository")
//        inner class UserEntityRepositoryTest: ULongIdImplTest()
//
//    }
//
//    @Nested
//    @DisplayName("Workflows Module")
//    inner class RepositoryWorkflowsModuleTest {
//
//        @Nested
//        @DisplayName("DataPointRepository")
//        inner class DataPointEntityRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("PhaseRepository")
//        inner class PhaseRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("StepRepository")
//        inner class StepRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("ToolParamRepository")
//        inner class ToolParamRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("ToolRepository")
//        inner class ToolRepositoryTest: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("WorkflowFolderRepository")
//        inner class WorkflowFolderRepositoryTests: ULongIdImplTest()
//
//        @Nested
//        @DisplayName("WorkflowRepository")
//        inner class WorkflowRepositoryTest : ULongIdImplTest()
//
//    }
}