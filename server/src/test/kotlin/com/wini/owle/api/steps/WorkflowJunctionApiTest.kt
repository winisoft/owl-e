package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.WorkflowJunction
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class WorkflowJunctionApiTest : BaseLongPkApiTest<WorkflowJunction, WorkflowJunctionApi>() {

    override val route: String = "/api/workflow-junction"

    override fun getDto(id: Long): WorkflowJunction = DtoTestInstanceFactory.workflowJunctionDto(
        id = id
    )

    override val serializer: KSerializer<WorkflowJunction> = WorkflowJunction.serializer()
    
    override val listSerializer: KSerializer<List<WorkflowJunction>> = ListSerializer(WorkflowJunction.serializer())

    override fun getApiInstance(
        dll: DLL<Long, WorkflowJunction>,
        validator: BaseValidator<WorkflowJunction>
    ): WorkflowJunctionApi = WorkflowJunctionApi(dll, validator)

}