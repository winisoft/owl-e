package com.wini.owle

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.environs.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.environs.ToolPyPackDep
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.dto.runs.test.*
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.user.tags.Tag
import com.wini.owle.dto.user.tags.TagRelation
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


object Server {

    fun startServer() {


        try {
            embeddedServer(
                factory = Netty,
                port = SERVER_PORT,
                host = "0.0.0.0",
                module = { ktorModule() }
            ).start(wait = true)
        } catch (e: Exception) {
            println("Failed to start server: ${e.message}")
            e.printStackTrace()
        }
    }

    private val koinApp: KoinApplication = startKoin {
        printLogger(Level.DEBUG) // Add this
        modules(appModule)
    }

    private fun Application.ktorModule() {
//        val api = OpenAPIGen(
//            config = OpenAPIGen.Configuration(
//                api = OpenAPIModel(
//                    info = InfoModel().apply {
//                        title = "My API"
//                        version = "1.0.0"
//                        description = "API Docs"
//                    }
//                )
//            ),
//            pipeline = this
//        )
//        install(OpenAPIGen) {
//            swaggerUiPath = "./swaggerUI"
//            info {
//                title = "My API"
//                version = "1.0.0"
//                description = "API Docs"
//            }
//        }
        routing {

            install(ContentNegotiation) {
                json(Json { prettyPrint = true; isLenient = true; ignoreUnknownKeys = true })
            }

            koinApp.koin.run {
                get<LongIdEndpointImpl<PyPackageRepo>>().register(this@routing)
                get<LongIdEndpointImpl<PyPackage>>().register(this@routing)
                get<LongIdEndpointImpl<VersionSpec>>().register(this@routing)
                get<CompositeIdEndpointImpl<ToolPyPackDep>>().register(this@routing)
                get<LongIdEndpointImpl<BenchmarkReport>>().register(this@routing)
                get<LongIdEndpointImpl<StepRunLogLine>>().register(this@routing)
                get<LongIdEndpointImpl<StepExecution>>().register(this@routing)
                get<LongIdEndpointImpl<TestCase>>().register(this@routing)
                get<LongIdEndpointImpl<TestExpectation>>().register(this@routing)
                get<LongIdEndpointImpl<TestInput>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowTestResult>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowExecution>>().register(this@routing)
                get<LongIdEndpointImpl<ErrorWatchFn>>().register(this@routing)
                get<LongIdEndpointImpl<ErrorWatch>>().register(this@routing)
                get<LongIdEndpointImpl<Inquisition>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowJunction>>().register(this@routing)
                get<LongIdEndpointImpl<AppliedTransformArg>>().register(this@routing)
                get<LongIdEndpointImpl<AppliedTransform>>().register(this@routing)
                get<LongIdEndpointImpl<TransformArg>>().register(this@routing)
                get<CompositeIdEndpointImpl<TransformTargetType>>().register(this@routing)
                get<LongIdEndpointImpl<Transform>>().register(this@routing)
                get<LongIdEndpointImpl<TypeDeclaration>>().register(this@routing)
                get<LongIdEndpointImpl<TypeDefinition>>().register(this@routing)
                get<LongIdEndpointImpl<TypeParameter>>().register(this@routing)
                get<LongIdEndpointImpl<TypeSchemaRef>>().register(this@routing)
                get<LongIdEndpointImpl<TypeVariance>>().register(this@routing)
                get<CompositeIdEndpointImpl<TypeUnionMember>>().register(this@routing)
                get<LongIdEndpointImpl<PaymentMethod>>().register(this@routing)
                get<LongIdEndpointImpl<Tag>>().register(this@routing)
                get<LongIdEndpointImpl<User>>().register(this@routing)
                get<CompositeIdEndpointImpl<TagRelation>>().register(this@routing)
                get<LongIdEndpointImpl<Phase>>().register(this@routing)
                get<LongIdEndpointImpl<Step>>().register(this@routing)
                get<LongIdEndpointImpl<ToolParam>>().register(this@routing)
                get<LongIdEndpointImpl<Tool>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowFolder>>().register(this@routing)
                get<LongIdEndpointImpl<Workflow>>().register(this@routing)
            }
        }
    }
}