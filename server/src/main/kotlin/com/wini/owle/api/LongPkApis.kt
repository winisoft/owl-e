package com.wini.owle.api

import com.wini.owle.dll.user.PaymentMethodDll
import com.wini.owle.dll.workflows.PhaseDll
import com.wini.owle.services.user.TagDll
import com.wini.owle.dll.transforms.TransformationArgDll
import com.wini.owle.dll.typing.TypeDefinitionDll
import com.wini.owle.dll.typing.TypeSchemaRefDll
import com.wini.owle.dll.typing.TypeVarianceDll
import com.wini.owle.dto.*
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
            get("/api/prereq-injection") { getAll(context.request.call) }
            get("/api/prereq-injection/{id}") { getById(context.request.call) }
            post("/api/prereq-injection") { create(context.request.call) }
            post("/api/prereq-injection/batch") { createBatch(context.request.call) }
            put("/api/prereq-injection") { update(context.request.call) }
            put("/api/prereq-injection/batch") { updateBatch(context.request.call) }
            delete("/api/prereq-injection/{id}") { super.delete(context.request.call) }
            delete("/api/prereq-injection/batch") { deleteBatch(context.request.call) }
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
            get("/api/provision") { getAll(context.request.call) }
            get("/api/provision/{id}") { getById(context.request.call) }
            post("/api/provision") { create(context.request.call) }
            post("/api/provision/batch") { createBatch(context.request.call) }
            put("/api/provision") { update(context.request.call) }
            put("/api/provision/batch") { updateBatch(context.request.call) }
            delete("/api/provision/{id}") { super.delete(context.request.call) }
            delete("/api/provision/batch") { deleteBatch(context.request.call) }
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
            get("/api/revision") { getAll(context.request.call) }
            get("/api/revision/{id}") { getById(context.request.call) }
            post("/api/revision") { create(context.request.call) }
            post("/api/revision/batch") { createBatch(context.request.call) }
            put("/api/revision") { update(context.request.call) }
            put("/api/revision/batch") { updateBatch(context.request.call) }
            delete("/api/revision/{id}") { super.delete(context.request.call) }
            delete("/api/revision/batch") { deleteBatch(context.request.call) }
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
            get("/api/package-repo") { getAll(context.request.call) }
            get("/api/package-repo/{id}") { getById(context.request.call) }
            post("/api/package-repo") { create(context.request.call) }
            post("/api/package-repo/batch") { createBatch(context.request.call) }
            put("/api/package-repo") { update(context.request.call) }
            put("/api/package-repo/batch") { updateBatch(context.request.call) }
            delete("/api/package-repo/{id}") { super.delete(context.request.call) }
            delete("/api/package-repo/batch") { deleteBatch(context.request.call) }
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
            get("/api/version-spec") { getAll(context.request.call) }
            get("/api/version-spec/{id}") { getById(context.request.call) }
            post("/api/version-spec") { create(context.request.call) }
            post("/api/version-spec/batch") { createBatch(context.request.call) }
            put("/api/version-spec") { update(context.request.call) }
            put("/api/version-spec/batch") { updateBatch(context.request.call) }
            delete("/api/version-spec/{id}") { super.delete(context.request.call) }
            delete("/api/version-spec/batch") { deleteBatch(context.request.call) }
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
            get("/api/benchmarking-data") { getAll(context.request.call) }
            get("/api/benchmarking-data/{id}") { getById(context.request.call) }
            post("/api/benchmarking-data") { create(context.request.call) }
            post("/api/benchmarking-data/batch") { createBatch(context.request.call) }
            put("/api/benchmarking-data") { update(context.request.call) }
            put("/api/benchmarking-data/batch") { updateBatch(context.request.call) }
            delete("/api/benchmarking-data/{id}") { super.delete(context.request.call) }
            delete("/api/benchmarking-data/batch") { deleteBatch(context.request.call) }
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
            get("/api/execution-log") { getAll(context.request.call) }
            get("/api/execution-log/{id}") { getById(context.request.call) }
            post("/api/execution-log") { create(context.request.call) }
            post("/api/execution-log/batch") { createBatch(context.request.call) }
            put("/api/execution-log") { update(context.request.call) }
            put("/api/execution-log/batch") { updateBatch(context.request.call) }
            delete("/api/execution-log/{id}") { super.delete(context.request.call) }
            delete("/api/execution-log/batch") { deleteBatch(context.request.call) }
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
            get("/api/step-execution") { getAll(context.request.call) }
            get("/api/step-execution/{id}") { getById(context.request.call) }
            post("/api/step-execution") { create(context.request.call) }
            post("/api/step-execution/batch") { createBatch(context.request.call) }
            put("/api/step-execution") { update(context.request.call) }
            put("/api/step-execution/batch") { updateBatch(context.request.call) }
            delete("/api/step-execution/{id}") { super.delete(context.request.call) }
            delete("/api/step-execution/batch") { deleteBatch(context.request.call) }
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
            get("/api/test-case") { getAll(context.request.call) }
            get("/api/test-case/{id}") { getById(context.request.call) }
            post("/api/test-case") { create(context.request.call) }
            post("/api/test-case/batch") { createBatch(context.request.call) }
            put("/api/test-case") { update(context.request.call) }
            put("/api/test-case/batch") { updateBatch(context.request.call) }
            delete("/api/test-case/{id}") { super.delete(context.request.call) }
            delete("/api/test-case/batch") { deleteBatch(context.request.call) }
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
            get("/api/test-expected-outcome") { getAll(context.request.call) }
            get("/api/test-expected-outcome/{id}") { getById(context.request.call) }
            post("/api/test-expected-outcome") { create(context.request.call) }
            post("/api/test-expected-outcome/batch") { createBatch(context.request.call) }
            put("/api/test-expected-outcome") { update(context.request.call) }
            put("/api/test-expected-outcome/batch") { updateBatch(context.request.call) }
            delete("/api/test-expected-outcome/{id}") { super.delete(context.request.call) }
            delete("/api/test-expected-outcome/batch") { deleteBatch(context.request.call) }
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
            get("/api/test-input") { getAll(context.request.call) }
            get("/api/test-input/{id}") { getById(context.request.call) }
            post("/api/test-input") { create(context.request.call) }
            post("/api/test-input/batch") { createBatch(context.request.call) }
            put("/api/test-input") { update(context.request.call) }
            put("/api/test-input/batch") { updateBatch(context.request.call) }
            delete("/api/test-input/{id}") { super.delete(context.request.call) }
            delete("/api/test-input/batch") { deleteBatch(context.request.call) }
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
            get("/api/test-result") { getAll(context.request.call) }
            get("/api/test-result/{id}") { getById(context.request.call) }
            post("/api/test-result") { create(context.request.call) }
            post("/api/test-result/batch") { createBatch(context.request.call) }
            put("/api/test-result") { update(context.request.call) }
            put("/api/test-result/batch") { updateBatch(context.request.call) }
            delete("/api/test-result/{id}") { super.delete(context.request.call) }
            delete("/api/test-result/batch") { deleteBatch(context.request.call) }
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
            get("/api/workflow-execution") { getAll(context.request.call) }
            get("/api/workflow-execution/{id}") { getById(context.request.call) }
            post("/api/workflow-execution") { create(context.request.call) }
            post("/api/workflow-execution/batch") { createBatch(context.request.call) }
            put("/api/workflow-execution") { update(context.request.call) }
            put("/api/workflow-execution/batch") { updateBatch(context.request.call) }
            delete("/api/workflow-execution/{id}") { super.delete(context.request.call) }
            delete("/api/workflow-execution/batch") { deleteBatch(context.request.call) }
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
            get("/api/error-watch-fn") { getAll(context.request.call) }
            get("/api/error-watch-fn/{id}") { getById(context.request.call) }
            post("/api/error-watch-fn") { create(context.request.call) }
            post("/api/error-watch-fn/batch") { createBatch(context.request.call) }
            put("/api/error-watch-fn") { update(context.request.call) }
            put("/api/error-watch-fn/batch") { updateBatch(context.request.call) }
            delete("/api/error-watch-fn/{id}") { super.delete(context.request.call) }
            delete("/api/error-watch-fn/batch") { deleteBatch(context.request.call) }
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
            get("/api/error-watch") { getAll(context.request.call) }
            get("/api/error-watch/{id}") { getById(context.request.call) }
            post("/api/error-watch") { create(context.request.call) }
            post("/api/error-watch/batch") { createBatch(context.request.call) }
            put("/api/error-watch") { update(context.request.call) }
            put("/api/error-watch/batch") { updateBatch(context.request.call) }
            delete("/api/error-watch/{id}") { super.delete(context.request.call) }
            delete("/api/error-watch/batch") { deleteBatch(context.request.call) }
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
            get("/api/inquisition") { getAll(context.request.call) }
            get("/api/inquisition/{id}") { getById(context.request.call) }
            post("/api/inquisition") { create(context.request.call) }
            post("/api/inquisition/batch") { createBatch(context.request.call) }
            put("/api/inquisition") { update(context.request.call) }
            put("/api/inquisition/batch") { updateBatch(context.request.call) }
            delete("/api/inquisition/{id}") { super.delete(context.request.call) }
            delete("/api/inquisition/batch") { deleteBatch(context.request.call) }
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
            get("/api/workflow-junction") { getAll(context.request.call) }
            get("/api/workflow-junction/{id}") { getById(context.request.call) }
            post("/api/workflow-junction") { create(context.request.call) }
            post("/api/workflow-junction/batch") { createBatch(context.request.call) }
            put("/api/workflow-junction") { update(context.request.call) }
            put("/api/workflow-junction/batch") { updateBatch(context.request.call) }
            delete("/api/workflow-junction/{id}") { super.delete(context.request.call) }
            delete("/api/workflow-junction/batch") { deleteBatch(context.request.call) }
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
            get("/api/applied-transform-arg") { getAll(context.request.call) }
            get("/api/applied-transform-arg/{id}") { getById(context.request.call) }
            post("/api/applied-transform-arg") { create(context.request.call) }
            post("/api/applied-transform-arg/batch") { createBatch(context.request.call) }
            put("/api/applied-transform-arg") { update(context.request.call) }
            put("/api/applied-transform-arg/batch") { updateBatch(context.request.call) }
            delete("/api/applied-transform-arg/{id}") { super.delete(context.request.call) }
            delete("/api/applied-transform-arg/batch") { deleteBatch(context.request.call) }
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
            get("/api/applied-transform") { getAll(context.request.call) }
            get("/api/applied-transform/{id}") { getById(context.request.call) }
            post("/api/applied-transform") { create(context.request.call) }
            post("/api/applied-transform/batch") { createBatch(context.request.call) }
            put("/api/applied-transform") { update(context.request.call) }
            put("/api/applied-transform/batch") { updateBatch(context.request.call) }
            delete("/api/applied-transform/{id}") { super.delete(context.request.call) }
            delete("/api/applied-transform/batch") { deleteBatch(context.request.call) }
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
            get("/api/transformation-arg") { getAll(context.request.call) }
            get("/api/transformation-arg/{id}") { getById(context.request.call) }
            post("/api/transformation-arg") { create(context.request.call) }
            post("/api/transformation-arg/batch") { createBatch(context.request.call) }
            put("/api/transformation-arg") { update(context.request.call) }
            put("/api/transformation-arg/batch") { updateBatch(context.request.call) }
            delete("/api/transformation-arg/{id}") { super.delete(context.request.call) }
            delete("/api/transformation-arg/batch") { deleteBatch(context.request.call) }
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
            get("/api/transformation") { getAll(context.request.call) }
            get("/api/transformation/{id}") { getById(context.request.call) }
            post("/api/transformation") { create(context.request.call) }
            post("/api/transformation/batch") { createBatch(context.request.call) }
            put("/api/transformation") { update(context.request.call) }
            put("/api/transformation/batch") { updateBatch(context.request.call) }
            delete("/api/transformation/{id}") { super.delete(context.request.call) }
            delete("/api/transformation/batch") { deleteBatch(context.request.call) }
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
            get("/api/type-declaration") { getAll(context.request.call) }
            get("/api/type-declaration/{id}") { getById(context.request.call) }
            post("/api/type-declaration") { create(context.request.call) }
            post("/api/type-declaration/batch") { createBatch(context.request.call) }
            put("/api/type-declaration") { update(context.request.call) }
            put("/api/type-declaration/batch") { updateBatch(context.request.call) }
            delete("/api/type-declaration/{id}") { super.delete(context.request.call) }
            delete("/api/type-declaration/batch") { deleteBatch(context.request.call) }
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
            get("/api/type-definition") { getAll(context.request.call) }
            get("/api/type-definition/{id}") { getById(context.request.call) }
            post("/api/type-definition") { create(context.request.call) }
            post("/api/type-definition/batch") { createBatch(context.request.call) }
            put("/api/type-definition") { update(context.request.call) }
            put("/api/type-definition/batch") { updateBatch(context.request.call) }
            delete("/api/type-definition/{id}") { super.delete(context.request.call) }
            delete("/api/type-definition/batch") { deleteBatch(context.request.call) }
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
            get("/api/type-parameter") { getAll(context.request.call) }
            get("/api/type-parameter/{id}") { getById(context.request.call) }
            post("/api/type-parameter") { create(context.request.call) }
            post("/api/type-parameter/batch") { createBatch(context.request.call) }
            put("/api/type-parameter") { update(context.request.call) }
            put("/api/type-parameter/batch") { updateBatch(context.request.call) }
            delete("/api/type-parameter/{id}") { super.delete(context.request.call) }
            delete("/api/type-parameter/batch") { deleteBatch(context.request.call) }
        }
    }
}

class TypeSchemaRefApi(dll: TypeSchemaRefDll) : GenericLongPkApi<TypeSchemaRefDto>(
    dll = dll,
    clazz = TypeSchemaRefDto::class,
    serializer = TypeSchemaRefDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-schema-ref") { getAll(context.request.call) }
            get("/api/type-schema-ref/{id}") { getById(context.request.call) }
            post("/api/type-schema-ref") { create(context.request.call) }
            post("/api/type-schema-ref/batch") { createBatch(context.request.call) }
            put("/api/type-schema-ref") { update(context.request.call) }
            put("/api/type-schema-ref/batch") { updateBatch(context.request.call) }
            delete("/api/type-schema-ref/{id}") { super.delete(context.request.call) }
            delete("/api/type-schema-ref/batch") { deleteBatch(context.request.call) }
        }
    }
}

class DataPointsApi(
        dll: DataPointDll
) : GenericLongPkApi<DataPointDto>(
    dll = dll,
    clazz = DataPointDto::class,
    serializer = DataPointDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/data-points") { getAll(context.request.call) }
            get("/api/data-points/{id}") { getById(context.request.call) }
            post("/api/data-points") { create(context.request.call) }
            post("/api/data-points/batch") { createBatch(context.request.call) }
            put("/api/data-points") { update(context.request.call) }
            put("/api/data-points/batch") { updateBatch(context.request.call) }
            delete("/api/data-points/{id}") { super.delete(context.request.call) }
            delete("/api/data-points/batch") { deleteBatch(context.request.call) }
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
            get("/api/type-bounds") { getAll(context.request.call) }
            get("/api/type-bounds/{id}") { getById(context.request.call) }
            post("/api/type-bounds") { create(context.request.call) }
            post("/api/type-bounds/batch") { createBatch(context.request.call) }
            put("/api/type-bounds") { update(context.request.call) }
            put("/api/type-bounds/batch") { updateBatch(context.request.call) }
            delete("/api/type-bounds/{id}") { super.delete(context.request.call) }
            delete("/api/type-bounds/batch") { deleteBatch(context.request.call) }
        }
    }
}

class TypeVarianceApi(dll: TypeVarianceDll) : GenericLongPkApi<TypeVarianceDto>(
    dll = dll,
    clazz = TypeVarianceDto::class,
    serializer = TypeVarianceDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") { getAll(context.request.call) }
            get("/api/type-variance/{id}") { getById(context.request.call) }
            post("/api/type-variance") { create(context.request.call) }
            post("/api/type-variance/batch") { createBatch(context.request.call) }
            put("/api/type-variance") { update(context.request.call) }
            put("/api/type-variance/batch") { updateBatch(context.request.call) }
            delete("/api/type-variance/{id}") { super.delete(context.request.call) }
            delete("/api/type-variance/batch") { deleteBatch(context.request.call) }
        }
    }
}

class PaymentMethodApi(dll: PaymentMethodDll) : GenericLongPkApi<PaymentMethodDto>(
    dll = dll,
    clazz = PaymentMethodDto::class,
    serializer = PaymentMethodDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/payment-method") { getAll(context.request.call) }
            get("/api/payment-method/{id}") { getById(context.request.call) }
            post("/api/payment-method") { create(context.request.call) }
            post("/api/payment-method/batch") { createBatch(context.request.call) }
            put("/api/payment-method") { update(context.request.call) }
            put("/api/payment-method/batch") { updateBatch(context.request.call) }
            delete("/api/payment-method/{id}") { super.delete(context.request.call) }
            delete("/api/payment-method/batch") { deleteBatch(context.request.call) }
        }
    }
}

class TagApi(dll: TagDll) : GenericLongPkApi<TagDto>(
    dll = dll,
    clazz = TagDto::class,
    serializer = TagDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag") { getAll(context.request.call) }
            get("/api/tag/{id}") { getById(context.request.call) }
            post("/api/tag") { create(context.request.call) }
            post("/api/tag/batch") { createBatch(context.request.call) }
            put("/api/tag") { update(context.request.call) }
            put("/api/tag/batch") { updateBatch(context.request.call) }
            delete("/api/tag/{id}") { super.delete(context.request.call) }
            delete("/api/tag/batch") { deleteBatch(context.request.call) }
        }
    }
}

class UserApi(dll: UserDll) : GenericLongPkApi<UserDto>(
    dll = dll,
    clazz = UserDto::class,
    serializer = UserDto.serializer()
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") { getAll(context.request.call) }
            get("/api/user/{id}") { getById(context.request.call) }
            post("/api/user") { create(context.request.call) }
            post("/api/user/batch") { createBatch(context.request.call) }
            put("/api/user") { update(context.request.call) }
            put("/api/user/batch") { updateBatch(context.request.call) }
            delete("/api/user/{id}") { super.delete(context.request.call) }
            delete("/api/user/batch") { deleteBatch(context.request.call) }
        }
    }
}

class PhaseApi(dll: PhaseDll) : GenericLongPkApi<PhaseDto>(
    dll = dll,
    clazz = PhaseDto::class,
    serializer = PhaseDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") { getAll(context.request.call) }
            get("/api/phase/{id}") { getById(context.request.call) }
            post("/api/phase") { create(context.request.call) }
            post("/api/phase/batch") { createBatch(context.request.call) }
            put("/api/phase") { update(context.request.call) }
            put("/api/phase/batch") { updateBatch(context.request.call) }
            delete("/api/phase/{id}") { super.delete(context.request.call) }
            delete("/api/phase/batch") { deleteBatch(context.request.call) }
        }
    }
}

class StepApi(dll: StepDll) : GenericLongPkApi<StepDto>(
    dll = dll,
    clazz = StepDto::class,
    serializer = StepDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step") { getAll(context.request.call) }
            get("/api/step/{id}") { getById(context.request.call) }
            post("/api/step") { create(context.request.call) }
            post("/api/step/batch") { createBatch(context.request.call) }
            put("/api/step") { update(context.request.call) }
            put("/api/step/batch") { updateBatch(context.request.call) }
            delete("/api/step/{id}") { super.delete(context.request.call) }
            delete("/api/step/batch") { deleteBatch(context.request.call) }
        }
    }
}

class ToolParamApi(dll: ToolParamDll) : GenericLongPkApi<ToolParamDto>(
    dll = dll,
    clazz = ToolParamDto::class,
    serializer = ToolParamDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") { getAll(context.request.call) }
            get("/api/tool-param/{id}") { getById(context.request.call) }
            post("/api/tool-param") { create(context.request.call) }
            post("/api/tool-param/batch") { createBatch(context.request.call) }
            put("/api/tool-param") { update(context.request.call) }
            put("/api/tool-param/batch") { updateBatch(context.request.call) }
            delete("/api/tool-param/{id}") { super.delete(context.request.call) }
            delete("/api/tool-param/batch") { deleteBatch(context.request.call) }
        }
    }
}

class ToolApi(dll: ToolDll) : GenericLongPkApi<ToolDto>(
    dll = dll,
    clazz = ToolDto::class,
    serializer = ToolDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") { getAll(context.request.call) }
            get("/api/tool/{id}") { getById(context.request.call) }
            post("/api/tool") { create(context.request.call) }
            post("/api/tool/batch") { createBatch(context.request.call) }
            put("/api/tool") { update(context.request.call) }
            put("/api/tool/batch") { updateBatch(context.request.call) }
            delete("/api/tool/{id}") { super.delete(context.request.call) }
            delete("/api/tool/batch") { deleteBatch(context.request.call) }
        }
    }
}

class WorkflowFolderApi(dll: WorkflowFolderDll) : GenericLongPkApi<WorkflowFolderDto>(
    dll = dll,
    clazz = WorkflowFolderDto::class,
    serializer = WorkflowFolderDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") { getAll(context.request.call) }
            get("/api/workflow-folder/{id}") { getById(context.request.call) }
            post("/api/workflow-folder") { create(context.request.call) }
            post("/api/workflow-folder/batch") { createBatch(context.request.call) }
            put("/api/workflow-folder") { update(context.request.call) }
            put("/api/workflow-folder/batch") { updateBatch(context.request.call) }
            delete("/api/workflow-folder/{id}") { super.delete(context.request.call) }
            delete("/api/workflow-folder/batch") { deleteBatch(context.request.call) }
        }
    }
}

class WorkflowApi(dll: WorkflowDll) : GenericLongPkApi<WorkflowDto>(
    dll = dll,
    clazz = WorkflowDto::class,
    serializer = WorkflowDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow") { getAll(context.request.call) }
            get("/api/workflow/{id}") { getById(context.request.call) }
            post("/api/workflow") { create(context.request.call) }
            post("/api/workflow/batch") { createBatch(context.request.call) }
            put("/api/workflow") { update(context.request.call) }
            put("/api/workflow/batch") { updateBatch(context.request.call) }
            delete("/api/workflow/{id}") { super.delete(context.request.call) }
            delete("/api/workflow/batch") { deleteBatch(context.request.call) }
        }
    }
}