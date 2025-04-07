// server/src/test/kotlin/com/wini/owle/api/steps/WorkflowJunctionApiTest.kt
package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.WorkflowJunctionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

open class WorkflowJunctionApiTest : BaseLongPkApiTest<WorkflowJunctionDto, WorkflowJunctionApi>() {
    override val route: String = "/api/workflow-junction"

    override fun getDto(): WorkflowJunctionDto = DtoTestInstanceFactory.workflowJunctionDto()

    override val serializer: KSerializer<WorkflowJunctionDto> = WorkflowJunctionDto.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowJunctionDto>> = ListSerializer(WorkflowJunctionDto.serializer())

    override fun getApiInstance(dll: DLL<Long, WorkflowJunctionDto>): WorkflowJunctionApi = WorkflowJunctionApi(dll)
}