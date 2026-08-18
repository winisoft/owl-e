package com.wini.owle

import com.wini.owle.TestHelper.connectToTestDb
import com.wini.owle.TestHelper.refreshTestDbData
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.typing.enums.TypeNodeKind
import com.wini.owle.dto.user.enums.OauthProvider
import com.wini.owle.dto.user.enums.UserStatus
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.environs.Distributions
import com.wini.owle.entity.environs.PythonVersions
import com.wini.owle.entity.environs.Versions
import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.entity.steps.DataPointWrites
import com.wini.owle.entity.steps.ToolEntity
import com.wini.owle.entity.steps.Tools
import com.wini.owle.entity.typing.TypeGenericParamEntity
import com.wini.owle.entity.typing.TypeGenericParams
import com.wini.owle.entity.typing.TypeInstantiationArgEntity
import com.wini.owle.entity.typing.TypeInstantiationArgs
import com.wini.owle.entity.typing.TypeInstantiationEntity
import com.wini.owle.entity.typing.TypeInstantiations
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeNodes
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.user.Users
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.DataPoints
import com.wini.owle.entity.workflows.GrouperEntity
import com.wini.owle.entity.workflows.Groupers
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.workflows.WorkflowRequirementEntity
import com.wini.owle.entity.workflows.WorkflowRequirements
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.junit.jupiter.api.Tag

@Tag("integration")
abstract class CompleteExampleIntegration {

    @BeforeEach
    fun setUp() {
        stopKoin()
        startKoin {
            modules(allModules)
            allowOverride(false)
        }

        connectToTestDb()

        refreshTestDbData(implicatedTables = listOf(
            DataPointWrites,
            Steps,
            Tools,
            TypeInstantiationArgs,
            TypeInstantiations,
            TypeGenericParams,
            WorkflowRequirements,
            DataPoints,
            TypeNodes,
            DistributionPackages,
            Distributions,
            Groupers,
            Workflows,
            PythonVersions,
            Users,
            Versions
        ))

        constructExampleData()
    }

    fun constructExampleData() {
        transaction {
            // --- Versions ---
            VersionEntity.new(11811449UL) {
                epoch = 0UL
                major = 1UL
                minor = 0UL
                micro = 0UL
                phase = PrereleasePhase.FINAL
                hasOverflow = false
            }
            VersionEntity.new(11811450UL) {
                epoch = 0UL
                major = 3UL
                minor = 8UL
                micro = 2UL
                phase = PrereleasePhase.FINAL
                hasOverflow = false
            }

            // --- Python Versions ---
            PythonVersionEntity.new(11811449UL){}

            // --- Users ---
            UserEntity.new(11711549UL) {
                email = "wini@whatisnotis.com"
                personalFirst = "wini"
                password = "test1234"
                oauthProvider = OauthProvider.GOOGLE
                userStatus = UserStatus.ACTIVE
            }

            // --- Workflows ---
            WorkflowEntity.new(11910249UL) {
                user = UserEntity[11711549UL]
                name = "Process and Display Some Data Set"
                description = "Retrieve some data about a thing, some specific thing, idk whatever. Then we clean it up, enrich the data set, process and render it."
                pythonVersion = PythonVersionEntity[11811449UL]
                parentFolder = null
            }

            // --- Groupers ---
            GrouperEntity.new(11210449UL) {
                workflow = WorkflowEntity[11910249UL]
                name = "Data Prep"
                description = "Perform Steps that Prepare our data set prior to its proper utilization"
                displayOrder = 0
            }
            GrouperEntity.new(11210450UL) {
                workflow = WorkflowEntity[11910249UL]
                name = "Prepare Initial Data Set"
                description = "Fetch and Verify the Json Data"
                displayOrder = 0
            }
            GrouperEntity.new(11210451UL) {
                workflow = WorkflowEntity[11910249UL]
                name = "Curate Data"
                description = "Restructure the collected data set to provide a greater utility for our use case"
                displayOrder = 1
            }

            // --- Distributions ---
            listOf("requests", "pandas", "jsonschema", "boto3", "matplotlib", "psycopg2-binary")
                .forEach { name ->
                    DistributionEntity.findById(name) ?: DistributionEntity.new(name){}
                }

            DistributionPackageEntity.new {
                version = VersionEntity[11811450UL]
                distribution = DistributionEntity.findById("matplotlib")!!
                versionString = "3.8.2"
                summary = "Python plotting package"
                keywords = null
                description = """
                    [![PyPi](https://img.shields.io/pypi/v/matplotlib)](https://pypi.org/project/matplotlib/)
                    [![Conda](https://img.shields.io/conda/vn/conda-forge/matplotlib)](https://anaconda.org/conda-forge/matplotlib)
                    [![Downloads](https://img.shields.io/pypi/dm/matplotlib)](https://pypi.org/project/matplotlib)

                    ![Matplotlib logotype](https://matplotlib.org/_static/logo2.svg)

                    Matplotlib is a comprehensive library for creating static, animated, and
                    interactive visualizations in Python.

                    Check out our [home page](https://matplotlib.org/) for more information.

                    ![image](https://matplotlib.org/_static/readme_preview.png)

                    Matplotlib produces publication-quality figures in a variety of hardcopy
                    formats and interactive environments across platforms. Matplotlib can be
                    used in Python scripts, Python/IPython shells, web application servers,
                    and various graphical user interface toolkits.
                """.trimIndent()
                descriptionContentType = "text/markdown"
                license = "PSF"
                homePage = "https://matplotlib.org"
                projectUrl = "https://pypi.org/project/matplotlib/"
                downloadUrl = "https://matplotlib.org/stable/users/installing/index.html"
                docsUrl = null
                yanked = false
                lastSerial = null
            }

            // --- Type Nodes ---
            TypeNodeEntity.new(11611048UL) {
                version = VersionEntity[11811449UL]
                namespace = "typing"
                name = "List"
                kind = TypeNodeKind.BASE
                isGeneric = true
            }
            TypeNodeEntity.new(11611047UL) {
                version = VersionEntity[11811449UL]
                namespace = "typing"
                name = "Dict"
                kind = TypeNodeKind.BASE
                isGeneric = true
            }
            TypeNodeEntity.new(11611046UL) {
                version = VersionEntity[11811449UL]
                namespace = "builtins"
                name = "str"
                kind = TypeNodeKind.BASE
                isGeneric = false
            }
            TypeNodeEntity.new(11611045UL) {
                version = VersionEntity[11811449UL]
                namespace = "typing"
                name = "Mapping"
                kind = TypeNodeKind.BASE
                isGeneric = true
            }
            TypeNodeEntity.new(11611049UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = "ValidationReport"
                kind = TypeNodeKind.BASE
                isGeneric = false
            }
            TypeNodeEntity.new(11611050UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = "Stats"
                kind = TypeNodeKind.BASE
                isGeneric = false
            }
            TypeNodeEntity.new(11611051UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = null
                kind = TypeNodeKind.INSTANTIATION
                isGeneric = false
            }
            TypeNodeEntity.new(11611052UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = null
                kind = TypeNodeKind.INSTANTIATION
                isGeneric = false
            }
            TypeNodeEntity.new(11611053UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = "Record"
                kind = TypeNodeKind.BASE
                isGeneric = false
            }
            TypeNodeEntity.new(11611054UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = null
                kind = TypeNodeKind.INSTANTIATION
                isGeneric = false
            }
            TypeNodeEntity.new(11611055UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = null
                kind = TypeNodeKind.INSTANTIATION
                isGeneric = false
            }
            TypeNodeEntity.new(11611056UL) {
                version = VersionEntity[11811449UL]
                namespace = null
                name = null
                kind = TypeNodeKind.INSTANTIATION
                isGeneric = false
            }

            // --- Data Points ---
            DataPointEntity.new(10011249UL) {
                workflow = WorkflowEntity[11910249UL]
                name = "api_endpoint"
                presetValue = "http://www.api.com"
                storeMethod = StoreMethod.TEXT
                typeNode = TypeNodeEntity[11611046UL] // str
            }
            DataPointEntity.new(10011250UL) {
                workflow = WorkflowEntity[11910249UL]
                name = "raw_json"
                presetValue = null
                storeMethod = StoreMethod.TEXT
                typeNode = TypeNodeEntity[11611046UL] // str
            }
            DataPointEntity.new(10011251UL) {
                workflow = WorkflowEntity[11910249UL]
                name = "validation_report"
                presetValue = null
                storeMethod = StoreMethod.TEXT
                typeNode = TypeNodeEntity[11611049UL] // ValidationReport
            }
            DataPointEntity.new(10011252UL) {
                workflow = WorkflowEntity[11910249UL]
                name = "schema_uri"
                presetValue = null
                storeMethod = StoreMethod.TEXT
                typeNode = TypeNodeEntity[11611046UL] // str
            }

            // --- Workflow Requirements ---
            WorkflowRequirementEntity.new {
                workflow = WorkflowEntity[11910249UL]
                requiredDist = DistributionEntity["requests"]
                requiredPackageVersion = null
                requiredExtraName = null
            }
            WorkflowRequirementEntity.new {
                workflow = WorkflowEntity[11910249UL]
                requiredDist = DistributionEntity["pandas"]
                requiredPackageVersion = null
                requiredExtraName = null
            }
            WorkflowRequirementEntity.new {
                workflow = WorkflowEntity[11910249UL]
                requiredDist = DistributionEntity["jsonschema"]
                requiredPackageVersion = null
                requiredExtraName = null
            }
            WorkflowRequirementEntity.new {
                workflow = WorkflowEntity[11910249UL]
                requiredDist = DistributionEntity["boto3"]
                requiredPackageVersion = null
                requiredExtraName = null
            }
            WorkflowRequirementEntity.new {
                workflow = WorkflowEntity[11910249UL]
                requiredDist = DistributionEntity["matplotlib"]
                requiredPackageVersion = VersionEntity.findById(11811450UL)?.id?.value
                requiredExtraName = null
            }
            WorkflowRequirementEntity.new {
                workflow = WorkflowEntity[11910249UL]
                requiredDist = DistributionEntity["psycopg2-binary"]
                requiredPackageVersion = null
                requiredExtraName = null
            }

            // --- Type Generic Params ---
            TypeGenericParamEntity.new(10311249UL) {
                classNode = TypeNodeEntity[11611048UL] // typing.List
                typevarNode = TypeNodeEntity[11611047UL] // typing.Dict
                position = 0UL
            }
            TypeGenericParamEntity.new(10311250UL) {
                classNode = TypeNodeEntity[11611047UL] // typing.Dict
                typevarNode = TypeNodeEntity[11611046UL] // builtins.str
                position = 0UL
            }
            TypeGenericParamEntity.new(10311251UL) {
                classNode = TypeNodeEntity[11611047UL] // typing.Dict
                typevarNode = TypeNodeEntity[11611050UL] // Stats
                position = 1UL
            }
            TypeGenericParamEntity.new(10311252UL) {
                classNode = TypeNodeEntity[11611048UL] // typing.List
                typevarNode = TypeNodeEntity[11611053UL] // Record
                position = 0UL
            }
            TypeGenericParamEntity.new(10311253UL) {
                classNode = TypeNodeEntity[11611045UL] // Mapping
                typevarNode = TypeNodeEntity[11611046UL] // str
                position = 0UL
            }
            TypeGenericParamEntity.new(10311254UL) {
                classNode = TypeNodeEntity[11611045UL] // Mapping
                typevarNode = TypeNodeEntity[11611050UL] // Stats
                position = 1UL
            }
            // --- Type Instantiations ---
            TypeInstantiationEntity.new(11610549UL) {
                instantiationNode = TypeNodeEntity[11611051UL] // List[Dict]
                originNode = TypeNodeEntity[11611048UL] // typing.List
            }
            TypeInstantiationEntity.new(11610550UL) {
                instantiationNode = TypeNodeEntity[11611052UL] // Dict[str, Stats]
                originNode = TypeNodeEntity[11611047UL] // typing.Dict
            }
            TypeInstantiationEntity.new(11610551UL) {
                instantiationNode = TypeNodeEntity[11611054UL] // List[Record]
                originNode = TypeNodeEntity[11611048UL] // typing.List
            }
            TypeInstantiationEntity.new(11610552UL) {
                instantiationNode = TypeNodeEntity[11611055UL] // Mapping[str, Stats]
                originNode = TypeNodeEntity[11611045UL] // Mapping
            }

            TypeInstantiationArgEntity.new(1169749UL) {
                instantiation = TypeInstantiationEntity[11610549UL] // List[Dict[str, Stats]]
                paramPosition = 0U
                paramNode = TypeNodeEntity[11611052UL] // Dict[str, Stats]
            }
            TypeInstantiationArgEntity.new(1169750UL) {
                instantiation = TypeInstantiationEntity[11610550UL] // Dict[str, Stats]
                paramPosition = 0U
                paramNode = TypeNodeEntity[11611046UL] // str
            }
            TypeInstantiationArgEntity.new(1169751UL) {
                instantiation = TypeInstantiationEntity[11610550UL] // Dict[str, Stats]
                paramPosition = 1U
                paramNode = TypeNodeEntity[11611050UL] // Stats
            }
            TypeInstantiationArgEntity.new(1169752UL) {
                instantiation = TypeInstantiationEntity[11610551UL] // List[Record]
                paramPosition = 0U
                paramNode = TypeNodeEntity[11611053UL] // Record
            }
            TypeInstantiationArgEntity.new(1169753UL) {
                instantiation = TypeInstantiationEntity[11610552UL] // Mapping[str, Stats]
                paramPosition = 0U
                paramNode = TypeNodeEntity[11611046UL] // str
            }
            TypeInstantiationArgEntity.new(1169754UL) {
                instantiation = TypeInstantiationEntity[11610552UL] // Mapping[str, Stats]
                paramPosition = 1U
                paramNode = TypeNodeEntity[11611050UL] // Stats
            }

            ToolEntity.new(11611149UL) {
                name = "fetch_json"
                signatureNode = null
                description = "Makes an HTTP request to pull the source json data"
                body = """
                    import requests
                    
                    def fetch_json(url):
                        pass
                """.trimIndent()
                returnTypeNode = TypeNodeEntity[11611046UL] // builtins.str
            }
            ToolEntity.new(11611150UL) {
                name = "validate_json"
                signatureNode = null
                description = "Validate a JSON string against a schema URI"
                body = """
                    def validate_json(data, schema_uri):
                        pass
                """.trimIndent()
                returnTypeNode = TypeNodeEntity[11611049UL]
            }
            ToolEntity.new(11611151UL) {
                name = "parse_normalize"
                signatureNode = null
                description = "Parse and flatten nested JSON into tabular structure"
                body = """
                    def parse_normalize():
                        pass
                """.trimIndent()
                returnTypeNode = TypeNodeEntity[11611051UL]
            }
            ToolEntity.new(11611152UL) {
                name = "db_enrich"
                signatureNode = null
                description = "Join external database metadata to parsed records"
                body = """
                    def db_enrich(records, conn):
                        pass
                """.trimIndent()
                returnTypeNode = TypeNodeEntity[11611051UL]
            }
            ToolEntity.new(11611153UL) {
                name = "compute_stats"
                signatureNode = null
                description = "Aggregate grouped statistics from enriched data"
                body = """
                    def compute_stats(rows, group_by):
                        pass
                """.trimIndent()
                returnTypeNode = TypeNodeEntity[11611052UL]
            }
            ToolEntity.new(11611154UL) {
                name = "render_and_upload"
                signatureNode = null
                description = "Render plots and upload to S3"
                body = """
                    def render_and_upload(stats, bucket):
                        pass
                """.trimIndent()
                returnTypeNode = TypeNodeEntity[11611046UL]
            }

//            ToolParamEntity.new(11611249UL) {
//                tool = ToolEntity[11611149UL]
//                name = "url"
//                typeNode= TypeNodeEntity[11611046UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611250UL) {
//                tool = ToolEntity[11611150UL]
//                name = "data"
//                typeNode=TypeNodeEntity[11611046UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611251UL) {
//                tool = ToolEntity[11611150UL]
//                name = "schema_uri"
//                typeNode=TypeNodeEntity[11611046UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611252UL) {
//                tool = ToolEntity[11611151UL]
//                name = "data"
//                typeNode=TypeNodeEntity[11611046UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611253UL) {
//                tool = ToolEntity[11611152UL]
//                name = "records"
//                typeNode=TypeNodeEntity[11611058UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611254UL) {
//                tool = ToolEntity[11611152UL]
//                name = "conn"
//                typeNode=TypeNodeEntity[11611046UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611255UL) {
//                tool = ToolEntity[11611153UL]
//                name = "rows"
//                typeNode=TypeNodeEntity[11611058UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611256UL) {
//                tool = ToolEntity[11611153UL]
//                name = "group_by"
//                typeNode=TypeNodeEntity[11611046UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611257UL) {
//                tool = ToolEntity[11611154UL]
//                name = "stats"
//                typeNode=TypeNodeEntity[11611055UL]
//                defaultValue=null
//            }
//            ToolParamEntity.new(11611258UL) {
//                tool = ToolEntity[11611154UL]
//                name = "bucket"
//                typeNode=TypeNodeEntity[11611046UL]
//                defaultValue=null
//            }
            StepEntity.new(11511649UL) {
                workflow = WorkflowEntity[11910249UL]
                parentGrouper  = GrouperEntity[11210450UL]
                tool = ToolEntity[11611149UL]
                name = "Fetch Remote JSON"
                description = "Flatten and restructure fetched data"
                isWorkflowHead = true
                displayOrder = 0
            }
            StepEntity.new(11511650UL) {
                workflow = WorkflowEntity[11910249UL]
                parentGrouper = GrouperEntity[11210450UL]
                tool = ToolEntity[11611150UL]
                name = "Schema Validate JSON"
                description = "Join on historical metadata via DB lookup"
                displayOrder = 1
            }
            StepEntity.new(11511651UL) {
                workflow = WorkflowEntity[11910249UL]
                parentGrouper = GrouperEntity[11210451UL]
                tool = ToolEntity[11611151UL]
                name = "Parse & Normalize Data"
                description = "Flatten and restructure fetched data"
                displayOrder = 0
            }
            StepEntity.new(11511652UL) {
                workflow = WorkflowEntity[11910249UL]
                parentGrouper = GrouperEntity[11210451UL]
                tool = ToolEntity[11611152UL]
                name = "Enrich from Database"
                description = "Join on historical metadata via DB lookup"
                displayOrder = 1
            }
            StepEntity.new(11511653UL) {
                workflow = WorkflowEntity[11910249UL]
                parentGrouper = null
                tool = ToolEntity[11611153UL]
                name = "Compute Summary Stats"
                description = "Group data and compute aggregates like mean, median, count"
                displayOrder = 2
            }
            StepEntity.new(11511654UL) {
                workflow = WorkflowEntity[11910249UL]
                parentGrouper = null
                tool = ToolEntity[11611154UL]
                name = "Render & Upload Plot"
                description = "Generate visual output and upload to S3"
                displayOrder = 3
            }

            DataPointWriteEntity.new(10011949UL) {
                dataPoint = DataPointEntity[10011250UL]
                step = StepEntity[11511649UL]
                transformOccurrence = null
            }

            DataPointWriteEntity.new(10011950UL) {
                dataPoint = DataPointEntity[10011251UL]
                step = StepEntity[11511650UL]
                transformOccurrence = null
            }
        }
    }
}
