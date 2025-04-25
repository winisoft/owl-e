package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.ToolParam
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class ToolParamApiTest : BaseLongPkApiTest<ToolParam, ToolParamApi>() {

    override val route: String = "api/tool-param"

    override fun getDto(id: Long): ToolParam = DtoTestInstanceFactory.toolParamDto(
        id = id
    )

    override val serializer: KSerializer<ToolParam> = ToolParam.serializer()
    
    override val listSerializer: KSerializer<List<ToolParam>> = ListSerializer(ToolParam.serializer())

    override fun getApiInstance(
        dll: DLL<Long, ToolParam>,
        validator: BaseValidator<ToolParam>
    ): ToolParamApi = ToolParamApi(dll, validator)

}