package com.wini.owle

import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.environs.Distributions
import com.wini.owle.entity.environs.EnvMarkerComparisons
import com.wini.owle.entity.environs.EnvMarkerExpressions
import com.wini.owle.entity.environs.EnvMarkerLogicals
import com.wini.owle.entity.environs.PackageDistRequirements
import com.wini.owle.entity.environs.PackagePythonRequirements
import com.wini.owle.entity.environs.PythonVersionSpecifiers
import com.wini.owle.entity.environs.PythonVersions
import com.wini.owle.entity.environs.VersionOverflows
import com.wini.owle.entity.environs.Versions
import com.wini.owle.entity.runs.BenchmarkReports
import com.wini.owle.entity.runs.TestCases
import com.wini.owle.entity.runs.TestInputs
import com.wini.owle.entity.runs.TestResults
import com.wini.owle.entity.runs.WorkflowExecutions
import com.wini.owle.entity.steps.ErrorWatchFns
import com.wini.owle.entity.steps.ErrorWatches
import com.wini.owle.entity.steps.Inquisitions
import com.wini.owle.entity.steps.DataPointInjections
import com.wini.owle.entity.steps.DataPointReads
import com.wini.owle.entity.steps.DataPointWriteArgs
import com.wini.owle.entity.steps.DataPointWrites
import com.wini.owle.entity.steps.Tools
import com.wini.owle.entity.steps.WorkflowJunctions
import com.wini.owle.entity.tags.Tags
import com.wini.owle.entity.tags.DistributionTags
import com.wini.owle.entity.tags.GrouperTags
import com.wini.owle.entity.tags.StepTags
import com.wini.owle.entity.tags.ToolTags
import com.wini.owle.entity.tags.TransformTags
import com.wini.owle.entity.tags.WorkflowFolderTags
import com.wini.owle.entity.tags.WorkflowTags
import com.wini.owle.entity.transforms.TransformOccurrenceArgs
import com.wini.owle.entity.transforms.TransformOccurrences
import com.wini.owle.entity.transforms.TransformArgs
import com.wini.owle.entity.transforms.TransformOccurrenceChains
import com.wini.owle.entity.transforms.Transforms
import com.wini.owle.entity.typing.TypeGenericParams
import com.wini.owle.entity.typing.TypeInstantiationArgs
import com.wini.owle.entity.typing.TypeInstantiations
import com.wini.owle.entity.typing.TypeNodeRelations
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.typing.TypeSchemaRefs
import com.wini.owle.entity.typing.TypeUnionMembers
import com.wini.owle.entity.typing.TypeVarBounds
import com.wini.owle.entity.typing.TypeVarConstraints
import com.wini.owle.entity.typing.TypeVarVariances
import com.wini.owle.entity.user.PaymentMethods
import com.wini.owle.entity.user.Users
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.WorkflowFolderRelations
import com.wini.owle.entity.workflows.WorkflowFolders
import com.wini.owle.entity.workflows.WorkflowRequirements
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object TestHelper {

    fun connectToTestDb() {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/owle_test",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "test_runner",
            password = "b0d!ed_forth"
        )
    }

    fun refreshTestDbData(implicatedTables: List<IdTable<*>>) {
        transaction {
            exec("SET FOREIGN_KEY_CHECKS=0;")
            SchemaUtils.drop(*implicatedTables.toTypedArray())
            exec("SET FOREIGN_KEY_CHECKS=1;")
            SchemaUtils.create(*implicatedTables.toTypedArray())
        }
    }

    /**
     * Defines for each type the database tables that must be refreshed before each in order to ensure a clean test environment.
     * The key to understand why is -- it's a lot faster to drop and recreate the tables if time is not wasted on irrelevant ones.
     */
    object ImplicatedTables {

        val ToolImplicatedTables: List<IdTable<*>> = listOf(
            Tools,
            ToolTags,
            ToolParams
        )

        val TransformImplicatedTables: List<IdTable<*>> = listOf(
            Transforms,
            TransformArgs,
            TransformOccurrences,
            TransformOccurrenceArgs,
            TransformOccurrenceChains,
            TransformTags
        )

        val TestImplicatedTables: List<IdTable<*>> = listOf(
            TestCases,
            TestOutcomeExpectations,
            TestInputs,
            TestResults
        )

        val UserImplicatedTables: List<IdTable<*>> = listOf(
            Users,
            PaymentMethods
        )

        val TestArtifactImplicatedTables: List<IdTable<*>> = listOf(
            TestOutcomeExpectations,
            TestInputs,
            TestResults,
            TestCases
        )

        val DataPointImplicatedTables: List<IdTable<*>> = listOf(
            DataPointWriteArgs,
            DataPointReads,
            DataPointWrites,
            DataPointInjections,
            //ProvisionDataPoints,
            //MetadataStepExclusions,
            //Metadata
        )

        val TransformOccurrenceImplicatedTables: List<IdTable<*>> = listOf(
            TransformOccurrenceArgs,
            TransformOccurrenceChains,
            TransformOccurrences
        )

        val ToolsAndTransformImplicatedTables: List<IdTable<*>> = listOf(
            ToolParams,
            Tools,
            ToolTags,
            TransformArgs,
            Transforms,
            TransformTags
        )

        val InquisitionImplicatedTables: List<IdTable<*>> = listOf(
            InquisitionArgs,
            Inquisitions
        )

        val ErrorWatchImplicatedTables: List<IdTable<*>> = listOf(
            ErrorWatchFns,
            ErrorWatches
        )

        val StepImplicatedTables: List<IdTable<*>> = listOf(
            WorkflowJunctions,
            StepTags,
            Steps
        )

        val PhaseImplicatedTables: List<IdTable<*>> = listOf(
            PhaseRelations,
            PhaseTags,
            Phases
        )

        val WorkflowFolderImplicatedTables: List<IdTable<*>> = listOf(
            Workflows,
            WorkflowFolderRelations,
            WorkflowFolderTags,
            WorkflowFolders,
            PythonVersions,
            Versions
        )

        val WorkflowImplicatedTables: List<IdTable<*>> = listOf(
            WorkflowTags,
            WorkflowExecutions,
            WorkflowRequirements,
            Workflows
        )

        val TagImplicatedTables: List<IdTable<*>> = listOf(
            PackageTags,
            DistributionTags,
            TypeNodeTags,
            PhaseTags,
            StepTags,
            ToolTags,
            TransformTags,
            WorkflowFolderTags,
            WorkflowTags,
            Tags
        )

        val TypeNodeImplicatedTables: List<IdTable<*>> = listOf(
            TypeSchemaRefs,
            TypeUnionMembers,
            TypeGenericParams,
            TypeInstantiationArgs,
            TypeInstantiations,
            TypeVarBounds,
            TypeVarConstraints,
            TypeVarVariances,
            TypeNodeRelations,
            TypeNodeTags,
            TypeNodes
        )

        val EnvMarkerImplicatedTables: List<IdTable<*>> = listOf(
            EnvMarkerComparisons,
            EnvMarkerLogicals,
            EnvMarkerExpressions
        )

        val PythonPackageImplicatedTables: List<IdTable<*>> = listOf(
            PackagePythonRequirements,
            PackageDistRequirements,
            PythonVersionSpecifiers,
            VersionOverflows,
            Versions,
            Distributions,
            DistributionPackages
        )

        val WorkflowExecutionImplicatedTables: List<IdTable<*>> = listOf(
            BenchmarkReports,
            WorkflowExecutions
        )

        val WorkflowRequirementImplicatedTables: List<IdTable<*>> = listOf(
            WorkflowRequirements
        )

        val VersionImplicatedTables: List<IdTable<*>> = listOf(
            Versions,
            VersionOverflows
        )

        val PackageImplicatedTables: List<IdTable<*>> = listOf(
            DistributionPackages,
            PackageDistRequirements,
            PackagePythonRequirements
        )

        val DistributionImplicatedTables: List<IdTable<*>> = listOf(
            Distributions
        )

        val PythonVersionImplicatedTables: List<IdTable<*>> = listOf(
            PythonVersions,
            PythonVersionSpecifiers
        )

        val PackageTagImplicatedTables: List<IdTable<*>> = listOf(
            PackageTags,
            DistributionTags
        )

        val TypeNodeTagImplicatedTables: List<IdTable<*>> = listOf(
            TypeNodeTags
        )

        val EnvMarkerExpressionImplicatedTables: List<IdTable<*>> = listOf(
            EnvMarkerComparisons,
            EnvMarkerLogicals,
            EnvMarkerExpressions
        )

        val MetadataImplicatedTables: List<IdTable<*>> = listOf(
            //Metadata
        )

        val MetadataStepExclusionImplicatedTables: List<IdTable<*>> = listOf(
            //MetadataStepExclusions
        )

        // 2. Data‑Point‑Specific
        val ProvisionDataPointImplicatedTables: List<IdTable<*>> = listOf(
            //ProvisionDataPoints
        )

        val DataPointReadImplicatedTables: List<IdTable<*>> = listOf(
            DataPointReads
        )

        val DataPointWriteImplicatedTables: List<IdTable<*>> = listOf(
            DataPointWrites
        )

        val DataPointWriteArgImplicatedTables: List<IdTable<*>> = listOf(
            DataPointWriteArgs
        )

        // 3. Transform‑Arg and Transform‑Tag
        val TransformArgImplicatedTables: List<IdTable<*>> = listOf(
            TransformArgs
        )

        val TransformTagImplicatedTables: List<IdTable<*>> = listOf(
            TransformTags
        )

        // 4. Type‑Node Substructures
        val TypeSchemaRefImplicatedTables: List<IdTable<*>> = listOf(
            TypeSchemaRefs
        )

        val TypeUnionMemberImplicatedTables: List<IdTable<*>> = listOf(
            TypeUnionMembers
        )

        val TypevarBoundImplicatedTables: List<IdTable<*>> = listOf(
            TypeVarBounds
        )

        val TypevarConstraintImplicatedTables: List<IdTable<*>> = listOf(
            TypeVarConstraints
        )

        val TypevarVarianceImplicatedTables: List<IdTable<*>> = listOf(
            TypeVarVariances
        )

        val TypeGenericParamImplicatedTables: List<IdTable<*>> = listOf(
            TypeGenericParams
        )

        val TypeInstantiationImplicatedTables: List<IdTable<*>> = listOf(
            TypeInstantiations
        )

        val TypeInstantiationArgImplicatedTables: List<IdTable<*>> = listOf(
            TypeInstantiationArgs
        )

        val TypeNodeRelationImplicatedTables: List<IdTable<*>> = listOf(
            TypeNodeRelations
        )

        // 5. Workflow‑Folder & Phase Relations
        val WorkflowFolderRelationImplicatedTables: List<IdTable<*>> = listOf(
            WorkflowFolderRelations
        )

        val PhaseRelationImplicatedTables: List<IdTable<*>> = listOf(
            PhaseRelations
        )

        // 6. Workflow‑Junctions & Step‑Default‑Routes
        val WorkflowJunctionImplicatedTables: List<IdTable<*>> = listOf(
            WorkflowJunctions
        )

        // 7. Data‑Point‑Injection & Benchmark‑Reports
        val DataPointInjectionImplicatedTables: List<IdTable<*>> = listOf(
            DataPointInjections
        )

        val BenchmarkReportImplicatedTables: List<IdTable<*>> = listOf(
            BenchmarkReports
        )

        // 8. Payment Methods
        val PaymentMethodImplicatedTables: List<IdTable<*>> = listOf(
            PaymentMethods
        )

        val All: List<IdTable<*>> = listOf(
            TestImplicatedTables,
            TestArtifactImplicatedTables,
            UserImplicatedTables,
            PaymentMethodImplicatedTables,
            ToolImplicatedTables,
            TransformImplicatedTables,
            TestImplicatedTables,
            UserImplicatedTables,
            TestArtifactImplicatedTables,
            DataPointImplicatedTables,
            TransformOccurrenceImplicatedTables,
            ToolsAndTransformImplicatedTables,
            InquisitionImplicatedTables,
            ErrorWatchImplicatedTables,
            StepImplicatedTables,
            PhaseImplicatedTables,
            WorkflowFolderImplicatedTables,
            WorkflowImplicatedTables,
            TagImplicatedTables,
            TypeNodeImplicatedTables,
            EnvMarkerImplicatedTables,
            PythonPackageImplicatedTables,
            WorkflowExecutionImplicatedTables,
            WorkflowRequirementImplicatedTables,
            VersionImplicatedTables,
            PackageImplicatedTables,
            DistributionImplicatedTables,
            PythonVersionImplicatedTables,
            PackageTagImplicatedTables,
            TypeNodeTagImplicatedTables,
            EnvMarkerExpressionImplicatedTables,
            MetadataImplicatedTables,
            MetadataStepExclusionImplicatedTables,
            ProvisionDataPointImplicatedTables,
            DataPointReadImplicatedTables,
            DataPointWriteImplicatedTables,
            DataPointWriteArgImplicatedTables,
            TransformArgImplicatedTables,
            TransformTagImplicatedTables,
            TypeSchemaRefImplicatedTables,
            TypeUnionMemberImplicatedTables,
            TypevarBoundImplicatedTables,
            TypevarConstraintImplicatedTables,
            TypevarVarianceImplicatedTables,
            TypeGenericParamImplicatedTables,
            TypeInstantiationImplicatedTables,
            TypeInstantiationArgImplicatedTables,
            TypeNodeRelationImplicatedTables,
            WorkflowFolderRelationImplicatedTables,
            PhaseRelationImplicatedTables,
            WorkflowJunctionImplicatedTables,
            DataPointInjectionImplicatedTables,
            BenchmarkReportImplicatedTables,
        ).flatten()

    }

}