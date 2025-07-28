package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.workflows.ToolApi
import com.wini.owle.dll.DLL
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class ToolApiTest : BaseLongPkApiTest<Tool, ToolApi>() {

    override val route: String = "/api/tool"

    override fun getDto(id: Long): Tool = DtoTestInstanceFactory.toolDto(
        id = id
    )

    override val serializer: KSerializer<Tool> = Tool.serializer()
    
    override val listSerializer: KSerializer<List<Tool>> = ListSerializer(Tool.serializer())

    override fun getApiInstance(
        dll: DLL<Long, Tool>,
        validator: DtoValidator<Tool>
    ): ToolApi = ToolApi(dll, validator)

}