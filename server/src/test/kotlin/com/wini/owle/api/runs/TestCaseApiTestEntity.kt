package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.test.TestCaseApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.test.TestCase
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TestCaseApiTestEntity : BaseLongPkApiTest<TestCase, TestCaseApi>() {

    override val route: String = "/api/test-case"
    
    override fun getDto(id: Long): TestCase = DtoTestInstanceFactory.testCaseDto(
        id = id
    )
    
    override val serializer: KSerializer<TestCase> = TestCase.serializer()
    
    override val listSerializer: KSerializer<List<TestCase>> = ListSerializer(TestCase.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, TestCase>,
        validator: BaseValidator<TestCase>
    ): TestCaseApi = TestCaseApi(dll, validator)

}