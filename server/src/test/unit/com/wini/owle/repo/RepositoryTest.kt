package com.wini.owle.repo

import com.wini.owle.dto.DtoTest
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.entity.InstanceDbFakeEntityFactory.pythonVersionEntity
import com.wini.owle.entity.environs.version.PythonVersionEntity
import io.mockk.MockKMatcherScope
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@Nested
@DisplayName("Repository Tests")
open class RepositoryTest {


    @Nested
    @DisplayName("Environs Module")
    inner class RepositoryEnvironsModuleTest {

        @Nested
        @DisplayName("PythonVersionRepository")
        inner class PythonVersionRepositoryTest: ULongIdGenericRepoTest<PythonVersionEntity>() {
            override fun mockEntity(): PythonVersionEntity = pythonVersionEntity()
            override fun MockKMatcherScope.anyT(): PythonVersionEntity = any<PythonVersionEntity>()
        }

        @Nested
        @DisplayName("VersionSpecRepository")
        inner class VersionSpecifierRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("DistributionRepository")
        inner class DistributionRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("OverflowVersionComponentRepository")
        inner class OverflowVersionComponentRepositoryTest: ULongIdGenericRepoTest()



        @Nested
        @DisplayName("SpecifierElementRepository")
        inner class SpecifierElementRepositoryTest: ULongIdGenericRepoTest()

    }

    @Nested
    @DisplayName("Runs Module")
    inner class RepositoryRunsModuleTest {

        @Nested
        @DisplayName("BenchmarkingDataRepository")
        inner class BenchmarkingDataRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("ExecutionLogRepository")
        inner class ExecutionLogRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("StepBenchmarkingDataRepository")
        inner class StepBenchmarkingDatasRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("StepExecutionRepository")
        inner class StepExecutionRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TestCaseRepository")
        inner class TestCaseRepositoryTestEntity: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TestExpectedOutcomeRepository")
        inner class TestExpectedOutcomeRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TestInputRepository")
        inner class TestInputRepositoryTestEntity: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TestResultRepository")
        inner class TestResultRepositoryWorkflowTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("WorkflowExecutionRepository")
        inner class WorkflowExecutionRepositoryTests: ULongIdGenericRepoTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class RepositoryStepsModuleTest {

        @Nested
        @DisplayName("ErrorWatchRepository")
        inner class ErrorWatchEntityRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("ErrorWatchFnRepository")
        inner class ErrorWatchEntityFnRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("InquisitionRepository")
        inner class InquisitionEntityRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("PreReqInjectionRepository")
        inner class PreReqInjectionRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("ProvisionRepository")
        inner class ProvisionRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("RevisionRepository")
        inner class RevisionRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("WorkflowJunctionRepository")
        inner class WorkflowJunctionRepositoryTests: ULongIdGenericRepoTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class RepositoryTransformsModuleTest {

        @Nested
        @DisplayName("AppliedTransformArgRepository")
        inner class AppliedTransformEntityEntityArgRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("AppliedTransformRepository")
        inner class AppliedTransformEntityEntityRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TransformationArgRepository")
        inner class TransformationArgRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TransformationRepository")
        inner class TransformationRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TransformArgTargetTypeRepository")
        inner class TransformationTargetTypeTest: OwleRepoImplTest()

    }

    @Nested
    @DisplayName("Typing Module")
    inner class RepositoryTypingModuleTests {

        @Nested
        @DisplayName("TypeBoundRepository")
        inner class TypeBoundEntityRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TypeDeclarationRepository")
        inner class TypeDeclarationEntityRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TypeDefinitionRepository")
        inner class TypeDefinitionEntityRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TypeParameterRepository")
        inner class TypeParameterRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TypeSchemaRefRepository")
        inner class TypeSchemaRefRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TypeVarianceRepository")
        inner class TypeVariancesRepositoryTest: ULongIdGenericRepoTest()

    }

    @Nested
    @DisplayName("User Module")
    inner class RepositoryUserEntityModuleTest {

        @Nested
        @DisplayName("PaymentMethodRepository")
        inner class PaymentMethodEntityRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("TagRepository")
        inner class TagEntityRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("UserRepository")
        inner class UserEntityRepositoryTest: ULongIdGenericRepoTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class RepositoryWorkflowsModuleTest {

        @Nested
        @DisplayName("DataPointRepository")
        inner class DataPointEntityRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("PhaseRepository")
        inner class PhaseRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("StepRepository")
        inner class StepRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("ToolParamRepository")
        inner class ToolParamRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("ToolRepository")
        inner class ToolRepositoryTest: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("WorkflowFolderRepository")
        inner class WorkflowFolderRepositoryTests: ULongIdGenericRepoTest()

        @Nested
        @DisplayName("WorkflowRepository")
        inner class WorkflowRepositoryTest : ULongIdGenericRepoTest()

    }
}