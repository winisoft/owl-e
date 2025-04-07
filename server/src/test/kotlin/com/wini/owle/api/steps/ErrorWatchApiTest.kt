package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ErrorWatchDto
import kotlinx.serialization.KSerializer


class ErrorWatchApiTest : BaseLongPkApiTest<Long, ErrorWatchDto, ErrorWatchApi>() {
    override val route: String
        get() = "/api/error-watch"

    override fun getDto(): ErrorWatchDto = DtoTestInstanceFactory.errorWatchDto()

    override val serializer: KSerializer<ErrorWatchDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ErrorWatchDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, ErrorWatchDto>): ErrorWatchApi {
        TODO("Not yet implemented")
    }

}