package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.WorkflowJunctionDto
import kotlinx.serialization.KSerializer


class WorkflowJunctionApiTest : BaseLongPkApiTest<Long, WorkflowJunctionDto, WorkflowJunctionApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): WorkflowJunctionDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<WorkflowJunctionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<WorkflowJunctionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, WorkflowJunctionDto>): WorkflowJunctionApi {
        TODO("Not yet implemented")
    }

}