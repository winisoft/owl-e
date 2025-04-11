package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.ToolDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class ToolApiTest : BaseLongPkApiTest<ToolDto, ToolApi>() {

    override val route: String = "/api/tool"

    override fun getDto(id: Long): ToolDto = DtoTestInstanceFactory.toolDto(
        id = id
    )

    override val serializer: KSerializer<ToolDto> = ToolDto.serializer()
    
    override val listSerializer: KSerializer<List<ToolDto>> = ListSerializer(ToolDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, ToolDto>,
        validator: BaseValidator<ToolDto>
    ): ToolApi = ToolApi(dll, validator)

}