package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.WorkflowExecutionDto
import kotlinx.serialization.KSerializer


class WorkflowExecutionApiTest : BaseLongPkApiTest<Long, WorkflowExecutionDto, WorkflowExecutionApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): WorkflowExecutionDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<WorkflowExecutionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<WorkflowExecutionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, WorkflowExecutionDto>): WorkflowExecutionApi {
        TODO("Not yet implemented")
    }

}