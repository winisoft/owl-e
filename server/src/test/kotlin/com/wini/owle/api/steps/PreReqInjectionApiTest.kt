package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.PreReqInjectionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PreReqInjectionApiTest : BaseLongPkApiTest<PreReqInjectionDto, PreReqInjectionApi>() {

    override val route: String = "/api/prereq-injection"

    override fun getDto(id: Long): PreReqInjectionDto = DtoTestInstanceFactory.preReqInjectionDto(
        id = id
    )

    override val serializer: KSerializer<PreReqInjectionDto> = PreReqInjectionDto.serializer()
    
    override val listSerializer: KSerializer<List<PreReqInjectionDto>> = ListSerializer(PreReqInjectionDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, PreReqInjectionDto>,
        validator: BaseValidator<PreReqInjectionDto>
    ): PreReqInjectionApi = PreReqInjectionApi(dll, validator)

}