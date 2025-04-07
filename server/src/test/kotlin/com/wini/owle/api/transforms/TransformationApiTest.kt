package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.TransformationDto
import kotlinx.serialization.KSerializer


class TransformationApiTest : BaseLongPkApiTest<Long, TransformationDto, TransformationApi>() {
    override val route: String
        get() = "/api/transformation"

    override fun getDto(): TransformationDto = DtoTestInstanceFactory.transformationDto()

    override val serializer: KSerializer<TransformationDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TransformationDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TransformationDto>): TransformationApi {
        TODO("Not yet implemented")
    }

}