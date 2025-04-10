package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class WorkflowApiTest: BaseLongPkApiTest<WorkflowDto, WorkflowApi>() {

    override val route: String = "/api/workflow"

    override fun getApiInstance(
        dll: DLL<Long, WorkflowDto>,
        validator: DtoValidator<WorkflowDto>
    ): WorkflowApi = WorkflowApi(dll = dll, validator = validator)

    override fun getDto(id: Long): WorkflowDto = DtoTestInstanceFactory.workflowDto(
        id = id
    )

    override val serializer: KSerializer<WorkflowDto> = WorkflowDto.serializer()

    override val listSerializer: KSerializer<List<WorkflowDto>> = ListSerializer(WorkflowDto.serializer())

}
