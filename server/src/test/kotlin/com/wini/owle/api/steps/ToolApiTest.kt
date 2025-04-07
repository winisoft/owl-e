package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolDto
import kotlinx.serialization.KSerializer

class ToolApiTest : BaseLongPkApiTest<Long, ToolDto, ToolApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): ToolDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<ToolDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ToolDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, ToolDto>): ToolApi {
        TODO("Not yet implemented")
    }

}