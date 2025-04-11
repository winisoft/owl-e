package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class AppliedTransformArgApiTest : BaseLongPkApiTest<AppliedTransformArgDto, AppliedTransformArgApi>() {

    override val route: String = "/api/applied-transform-arg"

    override fun getDto(id: Long): AppliedTransformArgDto = DtoTestInstanceFactory.appliedTransformArgDto(
        id = id
    )

    override val serializer: KSerializer<AppliedTransformArgDto> = AppliedTransformArgDto.serializer()
    
    override val listSerializer: KSerializer<List<AppliedTransformArgDto>> = ListSerializer(AppliedTransformArgDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, AppliedTransformArgDto>,
        validator: BaseValidator<AppliedTransformArgDto>
    ): AppliedTransformArgApi = AppliedTransformArgApi(dll, validator)

}