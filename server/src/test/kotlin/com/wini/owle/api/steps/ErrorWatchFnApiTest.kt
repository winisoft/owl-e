package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFnDto
import kotlinx.serialization.KSerializer


class ErrorWatchFnApiTest : BaseLongPkApiTest<Long, ErrorWatchFnDto, ErrorWatchFnApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): ErrorWatchFnDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<ErrorWatchFnDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ErrorWatchFnDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, ErrorWatchFnDto>): ErrorWatchFnApi {
        TODO("Not yet implemented")
    }

}