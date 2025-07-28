package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Workflow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class WorkflowApiTest: BaseLongPkApiTest<Workflow, WorkflowApi>() {

    override val route: String = "/api/workflow"

    override fun getApiInstance(
        dll: DLL<Long, Workflow>,
        validator: DtoValidator<Workflow>
    ): WorkflowApi = WorkflowApi(dll = dll, validator = validator)

    override fun getDto(id: Long): Workflow = DtoTestInstanceFactory.workflowDto(
        id = id
    )

    override val serializer: KSerializer<Workflow> = Workflow.serializer()

    override val listSerializer: KSerializer<List<Workflow>> = ListSerializer(Workflow.serializer())

}
