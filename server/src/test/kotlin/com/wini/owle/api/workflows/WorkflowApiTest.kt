package com.wini.owle.api.workflows

import com.wini.owle.api.BaseEndpointTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


class WorkflowApiTest: BaseEndpointTest<Long, WorkflowDto, WorkflowApi>() {

    override val route: String = "/api/workflow"

    override fun getApiInstance(dll: DLL<Long, WorkflowDto>): WorkflowApi = WorkflowApi(dll = dll)

    override fun getDto(): WorkflowDto = DtoTestInstanceFactory.workflowDto()

    override val serializer: KSerializer<WorkflowDto> = WorkflowDto.serializer()

    override val listSerializer: KSerializer<List<WorkflowDto>> = ListSerializer(WorkflowDto.serializer())

}
