package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolParamDto
import kotlinx.serialization.KSerializer


class ToolParamApiTest : BaseLongPkApiTest<Long, ToolParamDto, ToolParamApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): ToolParamDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<ToolParamDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ToolParamDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, ToolParamDto>): ToolParamApi {
        TODO("Not yet implemented")
    }

}