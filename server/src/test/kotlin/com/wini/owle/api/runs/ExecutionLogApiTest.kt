package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.ExecutionLogDto
import kotlinx.serialization.KSerializer


class ExecutionLogApiTest : BaseLongPkApiTest<Long, ExecutionLogDto, ExecutionLogApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): ExecutionLogDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<ExecutionLogDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ExecutionLogDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, ExecutionLogDto>): ExecutionLogApi {
        TODO("Not yet implemented")
    }

}