package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestCaseDto
import kotlinx.serialization.KSerializer


class TestCaseApiTest : BaseLongPkApiTest<Long, TestCaseDto, TestCaseApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TestCaseDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TestCaseDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TestCaseDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TestCaseDto>): TestCaseApi {
        TODO("Not yet implemented")
    }


}