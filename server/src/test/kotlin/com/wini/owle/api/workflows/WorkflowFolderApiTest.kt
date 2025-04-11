package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowFolderDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class WorkflowFolderApiTest : BaseLongPkApiTest<WorkflowFolderDto, WorkflowFolderApi>() {
    override val route: String = "/api/workflow-folder"
    
    override fun getDto(id: Long): WorkflowFolderDto = DtoTestInstanceFactory.workflowFolderDto(
        id = id
    )
    
    override val serializer: KSerializer<WorkflowFolderDto> = WorkflowFolderDto.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowFolderDto>> = ListSerializer(WorkflowFolderDto.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, WorkflowFolderDto>,
        validator: BaseValidator<WorkflowFolderDto>
    ): WorkflowFolderApi = WorkflowFolderApi(dll, validator)
}