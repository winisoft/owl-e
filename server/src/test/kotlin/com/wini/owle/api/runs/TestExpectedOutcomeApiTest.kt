package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class TestExpectedOutcomeApiTest : BaseLongPkApiTest<TestExpectedOutcomeDto, TestExpectedOutcomeApi>() {

    override val route: String = "/api/test-expected-outcome"
    
    override fun getDto(): TestExpectedOutcomeDto = DtoTestInstanceFactory.testExpectedOutcomeDto()
    
    override val serializer: KSerializer<TestExpectedOutcomeDto> = TestExpectedOutcomeDto.serializer()
    
    override val listSerializer: KSerializer<List<TestExpectedOutcomeDto>> = ListSerializer(TestExpectedOutcomeDto.serializer())
    
    override fun getApiInstance(dll: DLL<Long, TestExpectedOutcomeDto>): TestExpectedOutcomeApi = TestExpectedOutcomeApi(dll)
}