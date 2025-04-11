package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.TransformationArgDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TransformationArgApiTest : BaseLongPkApiTest<TransformationArgDto, TransformationArgApi>() {

    override val route: String = "/api/transformation-arg"

    override fun getDto(id: Long): TransformationArgDto = DtoTestInstanceFactory.transformationArgDto(
        id = id
    )

    override val serializer: KSerializer<TransformationArgDto> = TransformationArgDto.serializer()
    
    override val listSerializer: KSerializer<List<TransformationArgDto>> = ListSerializer(TransformationArgDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TransformationArgDto>,
        validator: BaseValidator<TransformationArgDto>
    ): TransformationArgApi = TransformationArgApi(dll, validator)

}