package com.wini.owle

import com.wini.owle.entity.BenchmarkingData
import com.wini.owle.entity.Users
import com.wini.owle.entity.WorkflowExecutions
import com.wini.owle.entity.*
import com.wini.owle.models.TransformationTargetType
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


object TestHelper {

    fun connectToTestDb() {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/owle_test_db",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "test_runner",
            password = "test1234"
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

        object WorkflowsModule {
            val DataPoint: List<IdTable<*>> = listOf(DataPoints, Workflows, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val Phase: List<IdTable<*>> = listOf(WorkflowFolders, Workflows, Steps, DataPoints, Phases)
            val Step: List<IdTable<*>> = listOf(Steps, Workflows, Tools, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val Tool: List<IdTable<*>> = listOf(Tools, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val ToolParam: List<IdTable<*>> = listOf(ToolParams, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val Workflow: List<IdTable<*>> = listOf(Workflows)
            val WorkflowFolder: List<IdTable<*>> = listOf(WorkflowFolders)
        }

        object UserModule {
            val PaymentMethod: List<IdTable<*>> = listOf(PaymentMethods, Users)
            val StepTag: List<IdTable<*>> = listOf(Steps, Tags)
            val Tag: List<IdTable<*>> = listOf(Tags, TagRelations, Users, StepTags, WorkflowTags)
            val TagRelation: List<IdTable<*>> = listOf(Tags)
            val User: List<IdTable<*>> = listOf(Users)
            val WorkflowTag: List<IdTable<*>> = listOf(Workflows, Tags)
        }

        object TypingModule {
            val TypeBound: List<IdTable<*>> = listOf(TypeBounds, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val TypeDeclaration: List<IdTable<*>> = listOf(TypeDeclarations, TypeDefinitions, VersionSpecs)
            val TypeDefinition: List<IdTable<*>> = listOf(TypeDefinitions, VersionSpecs)
            val TypeParameter: List<IdTable<*>> = listOf(TypeParameters, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val TypeSchemaRef: List<IdTable<*>> = listOf(TypeDefinitions, TypeDeclarations, TypeSchemaRefs)
            val TypeUnionMember: List<IdTable<*>> = listOf(TypeUnionMembers, TypeBounds, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val TypeVariance: List<IdTable<*>> = listOf(TypeVariances, TypeDeclarations, TypeDefinitions, VersionSpecs)
        }

        object TransformsModule {
            val AppliedTransformArg: List<IdTable<*>> = listOf(
                AppliedTransformArgs,
                AppliedTransforms,
                Transformations,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs,
                DataPoints,
                Workflows,
                Steps,
                Tools,
                TransformationArgs
            )
            val AppliedTransform: List<IdTable<*>> = listOf(
                AppliedTransforms,
                Transformations,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs,
                DataPoints,
                Workflows,
                Steps,
                Tools
            )
            val TransformationArg: List<IdTable<*>> = listOf(
                TransformationArgs,
                Transformations,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs
            )
            val Transformation: List<IdTable<*>> = listOf(Transformations, TypeDeclarations, TypeDefinitions, VersionSpecs)
            val TransformTargetType: List<IdTable<*>> = listOf(TransformTargetTypes)
        }

        object StepsModule {
            val ErrorWatch: List<IdTable<*>> = listOf(
                ErrorWatchFns,
                ErrorWatches,
                Steps,
                Workflows,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs
            )
            val ErrorWatchFn: List<IdTable<*>> = listOf(
                ErrorWatches,
                ErrorWatchFns,
                Steps,
                Workflows,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs
            )
            val Inquisition: List<IdTable<*>> = listOf(Inquisitions)
            val PreReqInjection: List<IdTable<*>> = listOf(
                PrereqInjections,
                WorkflowJunctions,
                Steps,
                Workflows,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs,
                Inquisitions,
                DataPoints
            )
            val Provision: List<IdTable<*>> = listOf(
                Provisions,
                Steps,
                Workflows,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs,
                ToolParams
            )
            val ProvisionDataPoint: List<IdTable<*>> = listOf(
                Provisions,
                DataPoints,
                Steps,
                Workflows,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs,
                ToolParams
            )
            val Revision: List<IdTable<*>> = listOf(
                Revisions,
                DataPoints,
                Workflows,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs,
                Steps,
                Tools,
                Transformations
            )
            val WorkflowJunction: List<IdTable<*>> = listOf(
                WorkflowJunctions,
                Steps,
                Workflows,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs,
                Inquisitions,
                DataPoints
            )
        }

        object EnvironsModule {
            val Package: List<IdTable<*>> = listOf(Packages, VersionSpecs)
            val PackageRepo: List<IdTable<*>> = listOf(PackageRepositories)
            val ToolPackageDependency: List<IdTable<*>> = listOf(ToolPackageDependencies)
            val VersionSpec: List<IdTable<*>> = listOf(VersionSpecs)
        }

        object RunsModule {
            val BenchmarkingDatas: List<IdTable<*>> = listOf(BenchmarkingData, WorkflowExecutions, Workflows, Users)
            val ExecutionLog: List<IdTable<*>> = listOf(
                ExecutionLogs,
                StepExecutions,
                WorkflowExecutions,
                Workflows,
                Users,
                Steps,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs
            )
            val StepBenchmarkingData: List<IdTable<*>> = listOf(
                StepBenchmarkingDatas,
                StepExecutions,
                WorkflowExecutions,
                Workflows,
                Users,
                Steps,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs
            )
            val StepExecution: List<IdTable<*>> = listOf(
                StepExecutions,
                WorkflowExecutions,
                Workflows,
                Users,
                Steps,
                Tools,
                TypeDeclarations,
                TypeDefinitions,
                VersionSpecs
            )
            val TestCase: List<IdTable<*>> = listOf(TestCases, Workflows, Users)
            val TestExpectedOutcome: List<IdTable<*>> = listOf(TestExpectedOutcomes, TestCases, Workflows, Users)
            val TestInput: List<IdTable<*>> = listOf(TestInputs, TestCases, Workflows, Users)
            val TestResult: List<IdTable<*>> = listOf(TestResults)
            val WorkflowExecution: List<IdTable<*>> = listOf(WorkflowExecutions, Workflows, Users)
        }

    }

}