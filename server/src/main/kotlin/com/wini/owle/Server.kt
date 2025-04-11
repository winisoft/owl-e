package com.wini.owle

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.runs.*
import com.wini.owle.api.steps.*
import com.wini.owle.api.transforms.*
import com.wini.owle.api.typing.*
import com.wini.owle.dto.environs.*
import com.wini.owle.dto.runs.*
import com.wini.owle.dto.steps.*
import com.wini.owle.dto.transforms.*
import com.wini.owle.dto.typing.*
import com.wini.owle.dto.user.*
import com.wini.owle.dto.workflows.*
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.environs.ToolPackageDependencyDto
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

        routing {

            install(ContentNegotiation) {
                json(Json { prettyPrint = true; isLenient = true; ignoreUnknownKeys = true })
            }

            koinApp.koin.run {
                get<LongIdEndpointImpl<PackageRepoDto>>().register(this@routing)
                get<LongIdEndpointImpl<PackageDto>>().register(this@routing)
                get<LongIdEndpointImpl<VersionSpecDto>>().register(this@routing)
                get<CompositeIdEndpointImpl<ToolPackageDependencyDto>>().register(this@routing)
                get<LongIdEndpointImpl<BenchmarkingDataDto>>().register(this@routing)
                get<LongIdEndpointImpl<ExecutionLogDto>>().register(this@routing)
                get<LongIdEndpointImpl<StepExecutionDto>>().register(this@routing)
                get<LongIdEndpointImpl<TestCaseDto>>().register(this@routing)
                get<LongIdEndpointImpl<TestExpectedOutcomeDto>>().register(this@routing)
                get<LongIdEndpointImpl<TestInputDto>>().register(this@routing)
                get<LongIdEndpointImpl<TestResultDto>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowExecutionDto>>().register(this@routing)
                get<LongIdEndpointImpl<ErrorWatchFnDto>>().register(this@routing)
                get<LongIdEndpointImpl<ErrorWatchDto>>().register(this@routing)
                get<LongIdEndpointImpl<InquisitionDto>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowJunctionDto>>().register(this@routing)
                get<LongIdEndpointImpl<AppliedTransformArgDto>>().register(this@routing)
                get<LongIdEndpointImpl<AppliedTransformDto>>().register(this@routing)
                get<LongIdEndpointImpl<TransformationArgDto>>().register(this@routing)
                get<CompositeIdEndpointImpl<TransformationTargetTypeDto>>().register(this@routing)
                get<LongIdEndpointImpl<TransformationDto>>().register(this@routing)
                get<LongIdEndpointImpl<TypeDeclarationDto>>().register(this@routing)
                get<LongIdEndpointImpl<TypeDefinitionDto>>().register(this@routing)
                get<LongIdEndpointImpl<TypeParameterDto>>().register(this@routing)
                get<LongIdEndpointImpl<TypeSchemaRefDto>>().register(this@routing)
                get<LongIdEndpointImpl<TypeVarianceDto>>().register(this@routing)
                get<CompositeIdEndpointImpl<TypeUnionMemberDto>>().register(this@routing)
                get<LongIdEndpointImpl<PaymentMethodDto>>().register(this@routing)
                get<LongIdEndpointImpl<TagDto>>().register(this@routing)
                get<LongIdEndpointImpl<UserDto>>().register(this@routing)
                get<CompositeIdEndpointImpl<TagRelationDto>>().register(this@routing)
                get<LongIdEndpointImpl<PhaseDto>>().register(this@routing)
                get<LongIdEndpointImpl<StepDto>>().register(this@routing)
                get<LongIdEndpointImpl<ToolParamDto>>().register(this@routing)
                get<LongIdEndpointImpl<ToolDto>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowFolderDto>>().register(this@routing)
                get<LongIdEndpointImpl<WorkflowDto>>().register(this@routing)
            }
        }
    }
}