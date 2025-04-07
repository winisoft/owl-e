// server/src/test/kotlin/com/wini/owle/api/runs/TestCaseApiTest.kt
package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.TestCaseDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class TestCaseApiTest : BaseLongPkApiTest<Long, TestCaseDto, TestCaseApi>() {
    override val route: String = "/api/test-case"
    
    override fun getDto(): TestCaseDto = DtoTestInstanceFactory.testCaseDto()
    
    override val serializer: KSerializer<TestCaseDto> = TestCaseDto.serializer()
    
    override val listSerializer: KSerializer<List<TestCaseDto>> = ListSerializer(TestCaseDto.serializer())
    
    override fun getApiInstance(dll: DLL<Long, TestCaseDto>): TestCaseApi = TestCaseApi(dll)
}