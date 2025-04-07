package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import kotlinx.serialization.KSerializer

class AppliedTransformArgApiTest : BaseLongPkApiTest<Long, AppliedTransformArgDto, AppliedTransformArgApi>() {
    override val route: String
        get() = "/api/applied-transform-arg"

    override fun getDto(): AppliedTransformArgDto = DtoTestInstanceFactory.appliedTransformArgDto()

    override val serializer: KSerializer<AppliedTransformArgDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<AppliedTransformArgDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, AppliedTransformArgDto>): AppliedTransformArgApi {
        TODO("Not yet implemented")
    }

}