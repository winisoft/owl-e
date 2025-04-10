package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.ToolParamDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class ToolParamApiTest : BaseLongPkApiTest<ToolParamDto, ToolParamApi>() {

    override val route: String = "api/tool-param"

    override fun getDto(id: Long): ToolParamDto = DtoTestInstanceFactory.toolParamDto(
        id = id
    )

    override val serializer: KSerializer<ToolParamDto> = ToolParamDto.serializer()
    
    override val listSerializer: KSerializer<List<ToolParamDto>> = ListSerializer(ToolParamDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, ToolParamDto>,
        validator: DtoValidator<ToolParamDto>
    ): ToolParamApi = ToolParamApi(dll, validator)

}