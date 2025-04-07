package com.wini.owle

import com.wini.owle.api.environs.PackageApi
import com.wini.owle.api.environs.PackageRepoApi
import com.wini.owle.api.environs.ToolPackageDependencyApi
import com.wini.owle.api.environs.VersionSpecApi
import com.wini.owle.api.runs.*
import com.wini.owle.api.steps.*
import com.wini.owle.api.transforms.*
import com.wini.owle.api.typing.*
import com.wini.owle.api.user.PaymentMethodApi
import com.wini.owle.api.user.TagApi
import com.wini.owle.api.user.TagRelationApi
import com.wini.owle.api.user.UserApi
import com.wini.owle.api.workflows.PhaseApi
import com.wini.owle.api.workflows.WorkflowApi
import com.wini.owle.api.workflows.WorkflowFolderApi
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ktor.ext.get


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

    private val koinApp: KoinApplication = startKoin { modules(appModule) }

    private fun Application.ktorModule() {

        routing {

            install(ContentNegotiation) {
                json(Json { prettyPrint = true; isLenient = true; ignoreUnknownKeys = true })
            }

            koinApp.koin.run {
                get<PackageRepoApi>().register(this@routing)
                get<PackageApi>().register(this@routing)
                get<VersionSpecApi>().register(this@routing)
                get<ToolPackageDependencyApi>().register(this@routing)
                get<BenchmarkingDataApi>().register(this@routing)
                get<ExecutionLogApi>().register(this@routing)
                get<StepExecutionApi>().register(this@routing)
                get<TestCaseApi>().register(this@routing)
                get<TestExpectedOutcomeApi>().register(this@routing)
                get<TestInputApi>().register(this@routing)
                get<TestResultApi>().register(this@routing)
                get<WorkflowExecutionApi>().register(this@routing)
                get<ErrorWatchFnApi>().register(this@routing)
                get<ErrorWatchApi>().register(this@routing)
                get<InquisitionApi>().register(this@routing)
                get<WorkflowJunctionApi>().register(this@routing)
                get<AppliedTransformArgApi>().register(this@routing)
                get<AppliedTransformApi>().register(this@routing)
                get<TransformationArgApi>().register(this@routing)
                get<TransformTargetTypeApi>().register(this@routing)
                get<TransformationApi>().register(this@routing)
                get<TypeDeclarationApi>().register(this@routing)
                get<TypeDefinitionApi>().register(this@routing)
                get<TypeParameterApi>().register(this@routing)
                get<TypeSchemaRefApi>().register(this@routing)
                get<TypeVarianceApi>().register(this@routing)
                get<TypeUnionMemberApi>().register(this@routing)
                get<PaymentMethodApi>().register(this@routing)
                get<TagApi>().register(this@routing)
                get<UserApi>().register(this@routing)
                get<TagRelationApi>().register(this@routing)
                get<PhaseApi>().register(this@routing)
                get<StepApi>().register(this@routing)
                get<ToolParamApi>().register(this@routing)
                get<ToolApi>().register(this@routing)
                get<WorkflowFolderApi>().register(this@routing)
                get<WorkflowApi>().register(this@routing)
            }
        }
    }
}