package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ProvisionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class ProvisionApiTest : BaseLongPkApiTest<ProvisionDto, ProvisionApi>() {

    override val route: String = "/api/provision"

    override fun getDto(id: Long): ProvisionDto = DtoTestInstanceFactory.provisionDto(
        id = id
    )

    override val serializer: KSerializer<ProvisionDto> = ProvisionDto.serializer()
    
    override val listSerializer: KSerializer<List<ProvisionDto>> = ListSerializer(ProvisionDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, ProvisionDto>,
        validator: DtoValidator<ProvisionDto>
    ): ProvisionApi = ProvisionApi(dll, validator)

}