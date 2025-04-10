package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.WorkflowExecutionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class WorkflowExecutionApiTest : BaseLongPkApiTest<WorkflowExecutionDto, WorkflowExecutionApi>() {

    override val route: String = "/api/workflow-execution"
    
    override fun getDto(id: Long): WorkflowExecutionDto = DtoTestInstanceFactory.workflowExecutionDto(
        id = id
    )
    
    override val serializer: KSerializer<WorkflowExecutionDto> = WorkflowExecutionDto.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowExecutionDto>> = ListSerializer(WorkflowExecutionDto.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, WorkflowExecutionDto>,
        validator: DtoValidator<WorkflowExecutionDto>
    ): WorkflowExecutionApi = WorkflowExecutionApi(dll, validator)

}