package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.InquisitionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class InquisitionApiTest : BaseLongPkApiTest<InquisitionDto, InquisitionApi>() {

    override val route: String = "/api/inquisition"

    override fun getDto(id: Long): InquisitionDto = DtoTestInstanceFactory.inquisitionDto(
        id = id
    )

    override val serializer: KSerializer<InquisitionDto> = InquisitionDto.serializer()
    
    override val listSerializer: KSerializer<List<InquisitionDto>> = ListSerializer(InquisitionDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, InquisitionDto>,
        validator: BaseValidator<InquisitionDto>
    ): InquisitionApi = InquisitionApi(dll, validator)

}