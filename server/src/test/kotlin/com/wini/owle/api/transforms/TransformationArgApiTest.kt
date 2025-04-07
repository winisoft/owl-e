package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformationArgDto
import kotlinx.serialization.KSerializer


class TransformationArgApiTest : BaseLongPkApiTest<Long, TransformationArgDto, TransformationArgApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TransformationArgDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TransformationArgDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TransformationArgDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TransformationArgDto>): TransformationArgApi {
        TODO("Not yet implemented")
    }

}