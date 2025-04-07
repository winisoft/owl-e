package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import kotlinx.serialization.KSerializer


class TestExpectedOutcomeApiTest : BaseLongPkApiTest<Long, TestExpectedOutcomeDto, TestExpectedOutcomeApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TestExpectedOutcomeDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TestExpectedOutcomeDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TestExpectedOutcomeDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TestExpectedOutcomeDto>): TestExpectedOutcomeApi {
        TODO("Not yet implemented")
    }


}