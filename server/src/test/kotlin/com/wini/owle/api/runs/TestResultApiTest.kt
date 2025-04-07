// server/src/test/kotlin/com/wini/owle/api/runs/TestResultApiTest.kt
package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.TestResultDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class TestResultApiTest : BaseLongPkApiTest<Long, TestResultDto, TestResultApi>() {
    override val route: String = "/api/test-result"
    
    override fun getDto(): TestResultDto = DtoTestInstanceFactory.testResultDto()
    
    override val serializer: KSerializer<TestResultDto> = TestResultDto.serializer()
    
    override val listSerializer: KSerializer<List<TestResultDto>> = ListSerializer(TestResultDto.serializer())
    
    override fun getApiInstance(dll: DLL<Long, TestResultDto>): TestResultApi = TestResultApi(dll)
}