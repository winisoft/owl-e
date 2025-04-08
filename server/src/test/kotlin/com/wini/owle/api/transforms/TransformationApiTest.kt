package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.TransformationDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class TransformationApiTest : BaseLongPkApiTest<TransformationDto, TransformationApi>() {

    override val route: String = "/api/transformation"

    override fun getDto(): TransformationDto = DtoTestInstanceFactory.transformationDto()

    override val serializer: KSerializer<TransformationDto> = TransformationDto.serializer()
    
    override val listSerializer: KSerializer<List<TransformationDto>> = ListSerializer(TransformationDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TransformationDto>,
        validator: (TransformationDto) -> Boolean
    ): TransformationApi = TransformationApi(dll, validator)

}