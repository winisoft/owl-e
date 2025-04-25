package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.WorkflowFolder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class WorkflowFolderApiTest : BaseLongPkApiTest<WorkflowFolder, WorkflowFolderApi>() {
    override val route: String = "/api/workflow-folder"
    
    override fun getDto(id: Long): WorkflowFolder = DtoTestInstanceFactory.workflowFolderDto(
        id = id
    )
    
    override val serializer: KSerializer<WorkflowFolder> = WorkflowFolder.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowFolder>> = ListSerializer(WorkflowFolder.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, WorkflowFolder>,
        validator: BaseValidator<WorkflowFolder>
    ): WorkflowFolderApi = WorkflowFolderApi(dll, validator)
}