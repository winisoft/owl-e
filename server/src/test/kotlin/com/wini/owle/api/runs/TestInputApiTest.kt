package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.TestInputDto
import com.wini.owle.validators.BaseValidator
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class TestInputApiTest : BaseLongPkApiTest<TestInputDto, TestInputApi>() {
    override val route: String = "/api/test-input"
    
    override fun getDto(id: Long): TestInputDto = DtoTestInstanceFactory.testInputDto(
        id = id
    )
    
    override val serializer: KSerializer<TestInputDto> = TestInputDto.serializer()
    
    override val listSerializer: KSerializer<List<TestInputDto>> = ListSerializer(TestInputDto.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, TestInputDto>,
        validator: BaseValidator<TestInputDto>
    ): TestInputApi = TestInputApi(dll, validator)
}