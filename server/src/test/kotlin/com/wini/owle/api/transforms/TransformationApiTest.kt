package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformationDto
import kotlinx.serialization.KSerializer


class TransformationApiTest : BaseLongPkApiTest<Long, TransformationDto, TransformationApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TransformationDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TransformationDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TransformationDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TransformationDto>): TransformationApi {
        TODO("Not yet implemented")
    }

}