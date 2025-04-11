package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.AppliedTransformDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class AppliedTransformApiTest : BaseLongPkApiTest<AppliedTransformDto, AppliedTransformApi>() {

    override val route: String = "/api/applied-transform"

    override fun getDto(id: Long): AppliedTransformDto = DtoTestInstanceFactory.appliedTransformDto(
        id = id
    )

    override val serializer: KSerializer<AppliedTransformDto> = AppliedTransformDto.serializer()
    
    override val listSerializer: KSerializer<List<AppliedTransformDto>> = ListSerializer(AppliedTransformDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, AppliedTransformDto>,
        validator: BaseValidator<AppliedTransformDto>
    ): AppliedTransformApi = AppliedTransformApi(dll, validator)

}