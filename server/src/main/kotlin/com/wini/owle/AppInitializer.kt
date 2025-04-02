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
import org.koin.core.context.GlobalContext.startKoin
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


object AppInitializer {

    init {
        val koin = startKoin {
            modules(appModule)
        }

        fun Application.module() {

            install(ContentNegotiation) {
                json()
            }


            routing {

                get<PackageRepoApi>().register(this)
                get<PackageApi>().register(this)
                get<VersionSpecApi>().register(this)
                get<ToolPackageDependencyApi>().register(this)

                get<BenchmarkingDataApi>().register(this)
                get<ExecutionLogApi>().register(this)
                get<StepExecutionApi>().register(this)
                get<TestCaseApi>().register(this)
                get<TestExpectedOutcomeApi>().register(this)
                get<TestInputApi>().register(this)
                get<TestResultApi>().register(this)
                get<WorkflowExecutionApi>().register(this)

                get<ErrorWatchFnApi>().register(this)
                get<ErrorWatchApi>().register(this)
                get<InquisitionApi>().register(this)
                get<WorkflowJunctionApi>().register(this)

                get<AppliedTransformArgApi>().register(this)
                get<AppliedTransformApi>().register(this)
                get<TransformationArgApi>().register(this)
                get<TransformTargetTypeApi>().register(this)
                get<TransformationApi>().register(this)

                get<TypeDeclarationApi>().register(this)
                get<TypeDefinitionApi>().register(this)
                get<TypeParameterApi>().register(this)
                get<TypeSchemaRefApi>().register(this)
                get<TypeVarianceApi>().register(this)
                get<TypeUnionMemberApi>().register(this)

                get<PaymentMethodApi>().register(this)
                get<TagApi>().register(this)
                get<UserApi>().register(this)
                get<TagRelationApi>().register(this)

                get<PhaseApi>().register(this)
                get<StepApi>().register(this)
                get<ToolParamApi>().register(this)
                get<ToolApi>().register(this)
                get<WorkflowFolderApi>().register(this)
                get<WorkflowApi>().register(this)
                get<ProvisionDataPointApi>().register(this)
            }
        }
    }
}