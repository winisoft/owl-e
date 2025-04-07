package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestInputDto
import kotlinx.serialization.KSerializer


class TestInputApiTest : BaseLongPkApiTest<Long, TestInputDto, TestInputApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TestInputDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TestInputDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TestInputDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TestInputDto>): TestInputApi {
        TODO("Not yet implemented")
    }


}