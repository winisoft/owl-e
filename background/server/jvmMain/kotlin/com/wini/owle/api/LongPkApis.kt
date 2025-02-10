package com.wini.owle.api

import com.wini.owle.dll.user.PaymentMethodDll
import com.wini.owle.dll.workflows.PhaseDll
import com.wini.owle.services.user.TagDll
import com.wini.owle.dll.transforms.TransformationArgDll
import com.wini.owle.dll.typing.TypeDefinitionDll
import com.wini.owle.dll.typing.TypeSchemaRefDll
import com.wini.owle.dll.typing.TypeVarianceDll
import com.wini.owle.dto.*
import com.wini.owle.dll.environs.PackageDll
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dll.environs.VersionSpecDll
import com.wini.owle.dll.runs.*
import com.wini.owle.dll.steps.*
import com.wini.owle.dll.transforms.AppliedTransformArgDll
import com.wini.owle.dll.transforms.AppliedTransformDll
import com.wini.owle.dll.transforms.TransformationDll
import com.wini.owle.dll.typing.TypeBoundsDll
import com.wini.owle.dll.typing.TypeDeclarationDll
import com.wini.owle.dll.typing.TypeParameterDll
import com.wini.owle.dll.user.UserDll
import com.wini.owle.dll.workflows.*
import io.ktor.server.routing.*


class PreReqInjectionApi(dll: PreReqInjectionDll): GenericLongPkApi<PreReqInjectionDto>(
    dll = dll,
    clazz = PreReqInjectionDto::class,
    serializer = PreReqInjectionDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {

        }
    }
}

class ProvisionApi(dll: ProvisionDll): GenericLongPkApi<ProvisionDto>(
    dll = dll,
    clazz = ProvisionDto::class,
    serializer = ProvisionDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }
}

class RevisionApi(dll: RevisionDll): GenericLongPkApi<RevisionDto>(
    dll = dll,
    clazz = RevisionDto::class,
    serializer = RevisionDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }
}

class PackageRepoApi(dll: PackageRepoDll): GenericLongPkApi<PackageRepoDto>(
    dll = dll,
    clazz = PackageRepoDto::class,
    serializer = PackageRepoDto.serializer()
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/items") { getAll(context.request.call) }
            get("/api/items/{id}") { getById(context.request.call) }
            post("/api/items") { create(context.request.call) }
            post("/api/items/batch") { createBatch(context.request.call) }
            put("/api/items") { update(context.request.call) }
            put("/api/items/batch") { updateBatch(context.request.call) }
            delete("/api/items/{id}") { super.delete(context.request.call) }
            delete("/api/items/batch") { deleteBatch(context.request.call) }
        }
    }

}

class PackageApi(dll: PackageDll): GenericLongPkApi<PackageDto>(
    dll = dll,
    clazz = PackageDto::class,
    serializer = PackageDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class VersionSpecApi(dll: VersionSpecDll) : GenericLongPkApi<VersionSpecDto>(
    dll = dll,
    clazz = VersionSpecDto::class,
    serializer = VersionSpecDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class BenchmarkingDataApi(dll: BenchmarkingDataDll) : GenericLongPkApi<BenchmarkingDataDto>(
    dll = dll,
    clazz = BenchmarkingDataDto::class,
    serializer = BenchmarkingDataDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class ExecutionLogApi(dll: ExecutionLogDll) : GenericLongPkApi<ExecutionLogDto>(
    dll = dll,
    clazz = ExecutionLogDto::class,
    serializer = ExecutionLogDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class StepExecutionApi(dll: StepExecutionDll) : GenericLongPkApi<StepExecutionDto>(
    dll = dll,
    clazz = StepExecutionDto::class,
    serializer = StepExecutionDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TestCaseApi(dll: TestCaseDll) : GenericLongPkApi<TestCaseDto>(
    dll = dll,
    clazz = TestCaseDto::class,
    serializer = TestCaseDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TestExpectedOutcomeApi(dll: TestExpectedOutcomeDll) : GenericLongPkApi<TestExpectedOutcomeDto>(
    dll = dll,
    clazz = TestExpectedOutcomeDto::class,
    serializer = TestExpectedOutcomeDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TestInputApi(dll: TestInputDll) : GenericLongPkApi<TestInputDto>(
    dll = dll,
    clazz = TestInputDto::class,
    serializer = TestInputDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TestResultApi(dll: TestResultDll) : GenericLongPkApi<TestResultDto>(
    dll = dll,
    clazz = TestResultDto::class,
    serializer = TestResultDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class WorkflowExecutionApi(dll: WorkflowExecutionDll) : GenericLongPkApi<WorkflowExecutionDto>(
    dll = dll,
    clazz = WorkflowExecutionDto::class,
    serializer = WorkflowExecutionDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class ErrorWatchFnApi(dll: ErrorWatchFnDll) : GenericLongPkApi<ErrorWatchFnDto>(
    dll = dll,
    clazz = ErrorWatchFnDto::class,
    serializer = ErrorWatchFnDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class ErrorWatchApi(dll: ErrorWatchDll) : GenericLongPkApi<ErrorWatchDto>(
    dll = dll,
    clazz = ErrorWatchDto::class,
    serializer = ErrorWatchDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class InquisitionApi(dll: InquisitionDll) : GenericLongPkApi<InquisitionDto>(
    dll = dll,
    clazz = InquisitionDto::class,
    serializer = InquisitionDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class WorkflowJunctionApi(dll: WorkflowJunctionDll) : GenericLongPkApi<WorkflowJunctionDto>(
    dll = dll,
    clazz = WorkflowJunctionDto::class,
    serializer = WorkflowJunctionDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class AppliedTransformArgApi(dll: AppliedTransformArgDll) : GenericLongPkApi<AppliedTransformArgDto>(
    dll = dll,
    clazz = AppliedTransformArgDto::class,
    serializer = AppliedTransformArgDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class AppliedTransformApi(dll: AppliedTransformDll) : GenericLongPkApi<AppliedTransformDto>(
    dll = dll,
    clazz = AppliedTransformDto::class,
    serializer = AppliedTransformDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TransformationArgApi(dll: TransformationArgDll) : GenericLongPkApi<TransformationArgDto>(
    dll = dll,
    clazz = TransformationArgDto::class,
    serializer = TransformationArgDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TransformationApi(dll: TransformationDll) : GenericLongPkApi<TransformationDto>(
    dll = dll,
    clazz = TransformationDto::class,
    serializer = TransformationDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TypeDeclarationApi(dll: TypeDeclarationDll) : GenericLongPkApi<TypeDeclarationDto>(
    dll = dll,
    clazz = TypeDeclarationDto::class,
    serializer = TypeDeclarationDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TypeDefinitionApi(dll: TypeDefinitionDll) : GenericLongPkApi<TypeDefinitionDto>(
    dll = dll,
    clazz = TypeDefinitionDto::class,
    serializer = TypeDefinitionDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TypeParameterApi(dll: TypeParameterDll) : GenericLongPkApi<TypeParameterDto>(
    dll = dll,
    clazz = TypeParameterDto::class,
    serializer = TypeParameterDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TypeSchemaRefApi(dll: TypeSchemaRefDll) : GenericLongPkApi<TypeSchemaRefDto>(
    dll = dll,
    clazz = TypeSchemaRefDto::class,
    serializer = TypeSchemaRefDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class DataPointsApi(
    dll: DataPointDll
): GenericLongPkApi<DataPointDto>(
    dll = dll,
    clazz = DataPointDto::class,
    serializer = DataPointDto.serializer()
) {

        override fun register(routing: Routing) {
        routing.apply {
            
        }
    }
}

class TypeBoundsApi(
    dll: TypeBoundsDll
) : GenericLongPkApi<TypeBoundsDto>(
    dll = dll,
    clazz = TypeBoundsDto::class,
    serializer = TypeBoundsDto.serializer()
) {

        override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TypeVarianceApi(dll: TypeVarianceDll) : GenericLongPkApi<TypeVarianceDto>(
    dll = dll,
    clazz = TypeVarianceDto::class,
    serializer = TypeVarianceDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class PaymentMethodApi(dll: PaymentMethodDll) : GenericLongPkApi<PaymentMethodDto>(
    dll = dll,
    clazz = PaymentMethodDto::class,
    serializer = PaymentMethodDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class TagApi(dll: TagDll) : GenericLongPkApi<TagDto>(
    dll = dll,
    clazz = TagDto::class,
    serializer = TagDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class UserApi(dll: UserDll) : GenericLongPkApi<UserDto>(
    dll = dll,
    clazz = UserDto::class,
    serializer = UserDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class PhaseApi(dll: PhaseDll) : GenericLongPkApi<PhaseDto>(
    dll = dll,
    clazz = PhaseDto::class,
    serializer = PhaseDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class StepApi(dll: StepDll) : GenericLongPkApi<StepDto>(
    dll = dll,
    clazz = StepDto::class,
    serializer = StepDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class ToolParamApi(dll: ToolParamDll) : GenericLongPkApi<ToolParamDto>(
    dll = dll,
    clazz = ToolParamDto::class,
    serializer = ToolParamDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class ToolApi(dll: ToolDll) : GenericLongPkApi<ToolDto>(
    dll = dll,
    clazz = ToolDto::class,
    serializer = ToolDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class WorkflowFolderApi(dll: WorkflowFolderDll) : GenericLongPkApi<WorkflowFolderDto>(
    dll = dll,
    clazz = WorkflowFolderDto::class,
    serializer = WorkflowFolderDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}

class WorkflowApi(dll: WorkflowDll) : GenericLongPkApi<WorkflowDto>(
    dll = dll,
    clazz = WorkflowDto::class,
    serializer = WorkflowDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            
        }
    }

}
