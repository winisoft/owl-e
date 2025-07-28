package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.workflow.WorkflowTestResultApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class TestResultApiWorkflowWorkflowTest : BaseLongPkApiTest<WorkflowTestResult, WorkflowTestResultApi>() {

    override val route: String = "/api/test-result"
    
    override fun getDto(id: Long): WorkflowTestResult = DtoTestInstanceFactory.testResultDto(
        id = id
    )
    
    override val serializer: KSerializer<WorkflowTestResult> = WorkflowTestResult.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowTestResult>> = ListSerializer(WorkflowTestResult.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, WorkflowTestResult>,
        validator: DtoValidator<WorkflowTestResult>
    ): WorkflowTestResultApi = WorkflowTestResultApi(dll, validator)

}