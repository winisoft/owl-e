package com.wini.owle.repo

import com.wini.owle.repo.environs.marker.EnvMarkerComparisonRepoTest
import com.wini.owle.repo.environs.marker.EnvMarkerExpressionRepoTest
import com.wini.owle.repo.environs.marker.EnvMarkerLogicalRepoTest
import com.wini.owle.repo.environs.pkg.DistributionPackageRepoTest
import com.wini.owle.repo.environs.pkg.PackagePythonRequirementRepoTest
import com.wini.owle.repo.environs.pkg.PackageRequiredDistRepoTest
import com.wini.owle.repo.environs.version.PythonVersionRepoTest
import com.wini.owle.repo.environs.version.PythonVersionSpecifierRepoTest
import com.wini.owle.repo.environs.version.VersionOverflowRepoTest
import com.wini.owle.repo.environs.version.VersionRepoTest
import com.wini.owle.repo.runs.test.BenchmarkReportRepoTest
import com.wini.owle.repo.runs.test.TestCaseRepoTest
import com.wini.owle.repo.runs.test.TestInputRepoTest
import com.wini.owle.repo.runs.workflow.TestResultRepoTest
import com.wini.owle.repo.steps.DataPointInjectionRepoTest
import com.wini.owle.repo.steps.DataPointWriteRepoTest
import com.wini.owle.repo.steps.ErrorWatchFnRepoTest
import com.wini.owle.repo.steps.ErrorWatchRepoTest
import com.wini.owle.repo.steps.InquisitionRepoTest
import com.wini.owle.repo.steps.WorkflowJunctionRepoTest
import com.wini.owle.repo.tags.TagRelationRepoTest
import com.wini.owle.repo.tags.TagRepoTest
import com.wini.owle.repo.tags.targets.DistributionTagRepoTest
import com.wini.owle.repo.tags.targets.PackageTagRepoTest
import com.wini.owle.repo.tags.targets.StepTagRepoTest
import com.wini.owle.repo.tags.targets.ToolTagRepoTest
import com.wini.owle.repo.tags.targets.TransformTagRepoTest
import com.wini.owle.repo.tags.targets.WorkflowFolderTagRepoTest
import com.wini.owle.repo.tags.targets.WorkflowTagRepoTest
import com.wini.owle.repo.transforms.TransformArgRepoTest
import com.wini.owle.repo.transforms.TransformOccurrenceArgRepoTest
import com.wini.owle.repo.transforms.TransformOccurrenceRepoTest
import com.wini.owle.repo.transforms.TransformRepoTest
import com.wini.owle.repo.transforms.TransformTargetTypeRepoTest
import com.wini.owle.repo.typing.TypeNodeRepoTest
import com.wini.owle.repo.typing.TypeSchemaRefRepoTest
import com.wini.owle.repo.typing.TypeUnionMemberRepoTest
import com.wini.owle.repo.typing.TypeVarBoundRepoTest
import com.wini.owle.repo.typing.TypeVarConstraintRepoTest
import com.wini.owle.repo.typing.TypeVarVarianceRepoTest
import com.wini.owle.repo.user.PaymentMethodRepoTest
import com.wini.owle.repo.user.UserRepoTest
import com.wini.owle.repo.workflows.DataPointRepoTest
import com.wini.owle.repo.workflows.StepRepoTest
import com.wini.owle.repo.workflows.ToolRepoTest
import com.wini.owle.repo.workflows.WorkflowFolderRepoTest
import com.wini.owle.repo.workflows.WorkflowMetadataRepoTest
import com.wini.owle.repo.workflows.WorkflowRepoTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


/**
 * Consolidates every per-type repository test into one nested tree so the JUnit report reads
 * as a map of the domain rather than a flat list of class names.
 *
 * Nothing is declared here. Each entry extends the `*RepoTest` that owns the type, which is
 * where its fixtures live and where one-off tests for that type belong. The suffixes differ
 * (`*RepositoryTest` here, `*RepoTest` there) so the two names never collide in the output.
 */
@DisplayName("Repository Tests")
abstract class RepositoryTest {

    @Nested
    @DisplayName("Environs Module")
    inner class RepositoryEnvironsModuleTest {

        @Nested
        @DisplayName("DistributionPackageRepository")
        inner class DistributionPackageRepositoryTest : DistributionPackageRepoTest()

        @Nested
        @DisplayName("EnvMarkerComparisonRepository")
        inner class EnvMarkerComparisonRepositoryTest : EnvMarkerComparisonRepoTest()

        @Nested
        @DisplayName("EnvMarkerExpressionRepository")
        inner class EnvMarkerExpressionRepositoryTest : EnvMarkerExpressionRepoTest()

        @Nested
        @DisplayName("EnvMarkerLogicalRepository")
        inner class EnvMarkerLogicalRepositoryTest : EnvMarkerLogicalRepoTest()

        @Nested
        @DisplayName("PackagePythonRequirementRepository")
        inner class PackagePythonRequirementRepositoryTest : PackagePythonRequirementRepoTest()

        @Nested
        @DisplayName("PackageRequiredDistRepository")
        inner class PackageRequiredDistRepositoryTest : PackageRequiredDistRepoTest()

        @Nested
        @DisplayName("PythonVersionRepository")
        inner class PythonVersionRepositoryTest : PythonVersionRepoTest()

        @Nested
        @DisplayName("PythonVersionSpecifierRepository")
        inner class PythonVersionSpecifierRepositoryTest : PythonVersionSpecifierRepoTest()

        @Nested
        @DisplayName("VersionOverflowRepository")
        inner class VersionOverflowRepositoryTest : VersionOverflowRepoTest()

        @Nested
        @DisplayName("VersionRepository")
        inner class VersionRepositoryTest : VersionRepoTest()

    }

    @Nested
    @DisplayName("Runs Module")
    inner class RepositoryRunsModuleTest {

        @Nested
        @DisplayName("BenchmarkReportRepository")
        inner class BenchmarkReportRepositoryTest : BenchmarkReportRepoTest()

        @Nested
        @DisplayName("TestCaseRepository")
        inner class TestCaseRepositoryTest : TestCaseRepoTest()

        @Nested
        @DisplayName("TestInputRepository")
        inner class TestInputRepositoryTest : TestInputRepoTest()

        @Nested
        @DisplayName("TestResultRepository")
        inner class TestResultRepositoryTest : TestResultRepoTest()

    }

    @Nested
    @DisplayName("Steps Module")
    inner class RepositoryStepsModuleTest {

        @Nested
        @DisplayName("DataPointInjectionRepository")
        inner class DataPointInjectionRepositoryTest : DataPointInjectionRepoTest()

        @Nested
        @DisplayName("DataPointWriteRepository")
        inner class DataPointWriteRepositoryTest : DataPointWriteRepoTest()

        @Nested
        @DisplayName("ErrorWatchFnRepository")
        inner class ErrorWatchFnRepositoryTest : ErrorWatchFnRepoTest()

        @Nested
        @DisplayName("ErrorWatchRepository")
        inner class ErrorWatchRepositoryTest : ErrorWatchRepoTest()

        @Nested
        @DisplayName("InquisitionRepository")
        inner class InquisitionRepositoryTest : InquisitionRepoTest()

        @Nested
        @DisplayName("WorkflowJunctionRepository")
        inner class WorkflowJunctionRepositoryTest : WorkflowJunctionRepoTest()

    }

    @Nested
    @DisplayName("Tags Module")
    inner class RepositoryTagsModuleTest {

        @Nested
        @DisplayName("DistributionTagRepository")
        inner class DistributionTagRepositoryTest : DistributionTagRepoTest()

        @Nested
        @DisplayName("PackageTagRepository")
        inner class PackageTagRepositoryTest : PackageTagRepoTest()

        @Nested
        @DisplayName("StepTagRepository")
        inner class StepTagRepositoryTest : StepTagRepoTest()

        @Nested
        @DisplayName("TagRelationRepository")
        inner class TagRelationRepositoryTest : TagRelationRepoTest()

        @Nested
        @DisplayName("TagRepository")
        inner class TagRepositoryTest : TagRepoTest()

        @Nested
        @DisplayName("ToolTagRepository")
        inner class ToolTagRepositoryTest : ToolTagRepoTest()

        @Nested
        @DisplayName("TransformTagRepository")
        inner class TransformTagRepositoryTest : TransformTagRepoTest()

        @Nested
        @DisplayName("WorkflowFolderTagRepository")
        inner class WorkflowFolderTagRepositoryTest : WorkflowFolderTagRepoTest()

        @Nested
        @DisplayName("WorkflowTagRepository")
        inner class WorkflowTagRepositoryTest : WorkflowTagRepoTest()

    }

    @Nested
    @DisplayName("Transforms Module")
    inner class RepositoryTransformsModuleTest {

        @Nested
        @DisplayName("TransformArgRepository")
        inner class TransformArgRepositoryTest : TransformArgRepoTest()

        @Nested
        @DisplayName("TransformOccurrenceArgRepository")
        inner class TransformOccurrenceArgRepositoryTest : TransformOccurrenceArgRepoTest()

        @Nested
        @DisplayName("TransformOccurrenceRepository")
        inner class TransformOccurrenceRepositoryTest : TransformOccurrenceRepoTest()

        @Nested
        @DisplayName("TransformRepository")
        inner class TransformRepositoryTest : TransformRepoTest()

        @Nested
        @DisplayName("TransformTargetTypeRepository")
        inner class TransformTargetTypeRepositoryTest : TransformTargetTypeRepoTest()

    }

    @Nested
    @DisplayName("Typing Module")
    inner class RepositoryTypingModuleTest {

        @Nested
        @DisplayName("TypeNodeRepository")
        inner class TypeNodeRepositoryTest : TypeNodeRepoTest()

        @Nested
        @DisplayName("TypeSchemaRefRepository")
        inner class TypeSchemaRefRepositoryTest : TypeSchemaRefRepoTest()

        @Nested
        @DisplayName("TypeUnionMemberRepository")
        inner class TypeUnionMemberRepositoryTest : TypeUnionMemberRepoTest()

        @Nested
        @DisplayName("TypeVarBoundRepository")
        inner class TypeVarBoundRepositoryTest : TypeVarBoundRepoTest()

        @Nested
        @DisplayName("TypeVarConstraintRepository")
        inner class TypeVarConstraintRepositoryTest : TypeVarConstraintRepoTest()

        @Nested
        @DisplayName("TypeVarVarianceRepository")
        inner class TypeVarVarianceRepositoryTest : TypeVarVarianceRepoTest()

    }

    @Nested
    @DisplayName("User Module")
    inner class RepositoryUserModuleTest {

        @Nested
        @DisplayName("PaymentMethodRepository")
        inner class PaymentMethodRepositoryTest : PaymentMethodRepoTest()

        @Nested
        @DisplayName("UserRepository")
        inner class UserRepositoryTest : UserRepoTest()

    }

    @Nested
    @DisplayName("Workflows Module")
    inner class RepositoryWorkflowsModuleTest {

        @Nested
        @DisplayName("DataPointRepository")
        inner class DataPointRepositoryTest : DataPointRepoTest()

        @Nested
        @DisplayName("StepRepository")
        inner class StepRepositoryTest : StepRepoTest()

        @Nested
        @DisplayName("ToolRepository")
        inner class ToolRepositoryTest : ToolRepoTest()

        @Nested
        @DisplayName("WorkflowFolderRepository")
        inner class WorkflowFolderRepositoryTest : WorkflowFolderRepoTest()

        @Nested
        @DisplayName("WorkflowMetadataRepository")
        inner class WorkflowMetadataRepositoryTest : WorkflowMetadataRepoTest()

        @Nested
        @DisplayName("WorkflowRepository")
        inner class WorkflowRepositoryTest : WorkflowRepoTest()

    }

}
