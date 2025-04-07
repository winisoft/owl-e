package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.AppliedTransformDto
import kotlinx.serialization.KSerializer


class AppliedTransformApiTest : BaseLongPkApiTest<Long, AppliedTransformDto, AppliedTransformApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): AppliedTransformDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<AppliedTransformDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<AppliedTransformDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, AppliedTransformDto>): AppliedTransformApi {
        TODO("Not yet implemented")
    }

}