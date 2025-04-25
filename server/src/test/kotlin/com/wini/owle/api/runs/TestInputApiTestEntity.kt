package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.test.TestInputApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.validators.BaseValidator
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class TestInputApiTestEntity : BaseLongPkApiTest<TestInput, TestInputApi>() {
    override val route: String = "/api/test-input"
    
    override fun getDto(id: Long): TestInput = DtoTestInstanceFactory.testInputDto(
        id = id
    )
    
    override val serializer: KSerializer<TestInput> = TestInput.serializer()
    
    override val listSerializer: KSerializer<List<TestInput>> = ListSerializer(TestInput.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, TestInput>,
        validator: BaseValidator<TestInput>
    ): TestInputApi = TestInputApi(dll, validator)
}