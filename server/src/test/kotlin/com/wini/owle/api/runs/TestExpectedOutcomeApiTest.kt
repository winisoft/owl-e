package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.test.TestExpectationApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestExpectation
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class TestExpectedOutcomeApiTest : BaseLongPkApiTest<TestExpectation, TestExpectationApi>() {

    override val route: String = "/api/test-expected-outcome"
    
    override fun getDto(id: Long): TestExpectation = DtoTestInstanceFactory.testExpectedOutcomeDto(
        id = id
    )
    
    override val serializer: KSerializer<TestExpectation> = TestExpectation.serializer()
    
    override val listSerializer: KSerializer<List<TestExpectation>> = ListSerializer(TestExpectation.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, TestExpectation>,
        validator: DtoValidator<TestExpectation>
    ): TestExpectationApi = TestExpectationApi(dll, validator)

}