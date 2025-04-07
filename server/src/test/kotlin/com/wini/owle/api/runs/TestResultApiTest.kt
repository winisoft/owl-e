package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestResultDto
import kotlinx.serialization.KSerializer


class TestResultApiTest : BaseLongPkApiTest<Long, TestResultDto, TestResultApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TestResultDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TestResultDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TestResultDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TestResultDto>): TestResultApi {
        TODO("Not yet implemented")
    }

}