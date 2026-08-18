package com.wini.owle.entity

//
//abstract class DbLayerIntegrationTests<ID: Any, T: Entity<ID>>: KoinTest {
//
//    abstract val implicatedTables: List<IdTable<*>>
//    abstract val testModule: Module
//    abstract fun getRepo(): OwleRepo<ID, T>
//    abstract fun getInstance(): T
//
//    @BeforeEach
//    fun setUp() {
//        stopKoin()
//        startKoin { modules(testModule) }
//        connectToTestDb()
//        refreshTestDbData(implicatedTables)
//    }
//
//    @Test
//    @DisplayName("Delete")
//    fun `test delete successfully removes the targeted record`() {
//        transaction {
//            val created = getInstance()
//            val id = created.id
//            val repo = getRepo()
//
//            repo.delete(id)
//
//            val fetched = repo.getById(id)
//            assertNull(fetched, "Entity should no longer exist after deletion")
//        }
//    }
//
//    fun connectToTestDb() {
//        Database.connect(
//            url = "jdbc:mysql://localhost:3306/owle_test",
//            driver = "com.mysql.cj.jdbc.Driver",
//            user = "test_runner",
//            password = "b0d!ed_forth"
//        )
//    }
//
//    fun refreshTestDbData(implicatedTables: List<IdTable<*>>) {
//        transaction {
//            exec("SET FOREIGN_KEY_CHECKS=0;")
//            SchemaUtils.drop(*implicatedTables.toTypedArray())
//            exec("SET FOREIGN_KEY_CHECKS=1;")
//            SchemaUtils.create(*implicatedTables.toTypedArray())
//        }
//    }
//
//    /**
//     * Defines for each type the database tables that must be refreshed before each in order to ensure a clean test environment.
//     * The key to understand why is -- it's a lot faster to drop and recreate the tables if time is not wasted on irrelevant ones.
//     */
//    object ImplicatedTables {
//
//        object WorkflowsModule {
//            val DataPoint: List<IdTable<*>> = listOf(DataPoints,
//                Workflows, TypeNodes)
//            val Phase: List<IdTable<*>> = listOf(WorkflowFolders, Workflows, Steps, DataPoints, Phases)
//            val Step: List<IdTable<*>> = listOf(Steps, Workflows, Tools, TypeNodes)
//            val Tool: List<IdTable<*>> = listOf(Tools, TypeNodes)
//            val ToolParam: List<IdTable<*>> = listOf(ToolParams, TypeNodes)
//            val Workflow: List<IdTable<*>> = listOf(Workflows)
//            val WorkflowFolder: List<IdTable<*>> = listOf(WorkflowFolders)
//        }
//
//        object UserModule {
//            val PaymentMethod: List<IdTable<*>> = listOf(PaymentMethods, Users)
//            val StepTag: List<IdTable<*>> = listOf(Steps, Tags)
//            val Tag: List<IdTable<*>> = listOf(Tags, TagRelations, Users, StepTags, WorkflowTags)
//            val TagRelation: List<IdTable<*>> = listOf(Tags)
//            val User: List<IdTable<*>> = listOf(Users)
//            val WorkflowTag: List<IdTable<*>> = listOf(Workflows, Tags)
//        }
//
//        object TypingModule {
//            val TypeBound: List<IdTable<*>> = listOf(TypeVarBounds, TypeNodes)
//            val TypeDeclaration: List<IdTable<*>> = listOf(TypeNodes)
//            val TypeParameter: List<IdTable<*>> = listOf(TypeParameters, TypeNodes)
//            val TypeSchemaRef: List<IdTable<*>> = listOf(TypeNodes, TypeSchemaRefs)
//            val TypeUnionMember: List<IdTable<*>> = listOf(TypeUnionMembers, TypeVarBounds, TypeNodes)
//            val TypeVariance: List<IdTable<*>> = listOf(TypeVarBounds, TypeNodes)
//        }
//
//        object TransformsModule {
//            val AppliedTransformArg: List<IdTable<*>> = listOf(
//                TransformOccurrenceArgs,
//                TransformOccurrences,
//                Transforms,
//                TypeNodes,
//                DataPoints,
//                Workflows,
//                Steps,
//                Tools,
//                TransformArgs
//            )
//            val AppliedTransform: List<IdTable<*>> = listOf(
//                TransformOccurrences,
//                Transforms,
//                TypeNodes,
//                DataPoints,
//                Workflows,
//                Steps,
//                Tools
//            )
//            val TransformationArg: List<IdTable<*>> = listOf(
//                TransformArgs,
//                Transforms,
//                TypeNodes
//            )
//            val Transformation: List<IdTable<*>> = listOf(Transforms, TypeNodes)
//            val TransformTargetType: List<IdTable<*>> = listOf(TransformTargetTypes)
//        }
//
//        object StepsModule {
//            val ErrorWatch: List<IdTable<*>> = listOf(
//                ErrorWatchFns,
//                ErrorWatches,
//                Steps,
//                Workflows,
//                Tools,
//                TypeNodes
//            )
//            val ErrorWatchFn: List<IdTable<*>> = listOf(
//                ErrorWatches,
//                ErrorWatchFns,
//                Steps,
//                Workflows,
//                Tools,
//                TypeNodes
//            )
//            val Inquisition: List<IdTable<*>> = listOf(Inquisitions)
//            val PreReqInjection: List<IdTable<*>> = listOf(
//                DataPointInjections,
//                WorkflowJunctions,
//                Steps,
//                Workflows,
//                Tools,
//                TypeNodes,
//                Inquisitions,
//                DataPoints
//            )
//            val Provision: List<IdTable<*>> = listOf(
//                DataPointReads,
//                Steps,
//                Workflows,
//                Tools,
//                TypeNodes,
//                ToolParams
//            )
//            val ProvisionDataPoint: List<IdTable<*>> = listOf(
//                DataPointReads,
//                DataPoints,
//                Steps,
//                Workflows,
//                Tools,
//                TypeNodes,
//                ToolParams
//            )
//            val Revision: List<IdTable<*>> = listOf(
//                DataPointWrites,
//                DataPoints,
//                Workflows,
//                TypeNodes,
//                Steps,
//                Tools,
//                Transforms
//            )
//            val WorkflowJunction: List<IdTable<*>> = listOf(
//                WorkflowJunctions,
//                Steps,
//                Workflows,
//                Tools,
//                TypeNodes,
//                Inquisitions,
//                DataPoints
//            )
//        }
//
//        object EnvironsModule {
//            val Package: List<IdTable<*>> = listOf()
//            val PackageRepo: List<IdTable<*>> = listOf()
//            val ToolPackageDependency: List<IdTable<*>> = listOf()
//            val VersionSpec: List<IdTable<*>> = listOf()
//        }
//
//        object RunsModule {
//            val BenchmarkingDatas: List<IdTable<*>> = listOf(WorkflowExecutions, Workflows, Users)
//            val ExecutionLog: List<IdTable<*>> = listOf(
//                ExecutionLogs,
//                StepExecutions,
//                WorkflowExecutions,
//                Workflows,
//                Users,
//                Steps,
//                Tools,
//                TypeNodes
//            )
//            val StepBenchmarkingData: List<IdTable<*>> = listOf(
//                StepExecutions,
//                WorkflowExecutions,
//                Workflows,
//                Users,
//                Steps,
//                Tools,
//                TypeNodes
//            )
//            val StepExecution: List<IdTable<*>> = listOf(
//                StepExecutions,
//                WorkflowExecutions,
//                Workflows,
//                Users,
//                Steps,
//                Tools,
//                TypeNodes
//            )
//            val TestCase: List<IdTable<*>> = listOf(TestCases, Workflows, Users)
//            val TestExpectedOutcome: List<IdTable<*>> = listOf(TestExpectedOutcomes, TestCases, Workflows, Users)
//            val TestInput: List<IdTable<*>> = listOf(TestInputs, TestCases, Workflows, Users)
//            val TestResult: List<IdTable<*>> = listOf(TestResults)
//            val WorkflowExecution: List<IdTable<*>> = listOf(WorkflowExecutions, Workflows, Users)
//        }
//
//    }
//
//}
