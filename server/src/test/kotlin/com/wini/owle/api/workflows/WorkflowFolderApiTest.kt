package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowFolderDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

open class WorkflowFolderApiTest : BaseLongPkApiTest<WorkflowFolderDto, WorkflowFolderApi>() {
    override val route: String = "/api/workflow-folder"
    
    override fun getDto(): WorkflowFolderDto = DtoTestInstanceFactory.workflowFolderDto()
    
    override val serializer: KSerializer<WorkflowFolderDto> = WorkflowFolderDto.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowFolderDto>> = ListSerializer(WorkflowFolderDto.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, WorkflowFolderDto>,
        validator: (WorkflowFolderDto) -> Boolean
    ): WorkflowFolderApi = WorkflowFolderApi(dll, validator)
}