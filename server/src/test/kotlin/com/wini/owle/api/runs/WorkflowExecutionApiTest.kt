package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.workflow.WorkflowExecutionApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class WorkflowExecutionApiTest : BaseLongPkApiTest<WorkflowExecution, WorkflowExecutionApi>() {

    override val route: String = "/api/workflow-execution"
    
    override fun getDto(id: Long): WorkflowExecution = DtoTestInstanceFactory.workflowExecutionDto(
        id = id
    )
    
    override val serializer: KSerializer<WorkflowExecution> = WorkflowExecution.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowExecution>> = ListSerializer(WorkflowExecution.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, WorkflowExecution>,
        validator: DtoValidator<WorkflowExecution>
    ): WorkflowExecutionApi = WorkflowExecutionApi(dll, validator)

}