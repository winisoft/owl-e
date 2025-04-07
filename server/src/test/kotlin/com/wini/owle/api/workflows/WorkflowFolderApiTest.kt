package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowFolderDto
import kotlinx.serialization.KSerializer

class WorkflowFolderApiTest : BaseLongPkApiTest<Long, WorkflowFolderDto, WorkflowFolderApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): WorkflowFolderDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<WorkflowFolderDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<WorkflowFolderDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, WorkflowFolderDto>): WorkflowFolderApi {
        TODO("Not yet implemented")
    }

}