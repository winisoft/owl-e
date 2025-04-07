package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.InquisitionDto
import kotlinx.serialization.KSerializer


class InquisitionApiTest : BaseLongPkApiTest<Long, InquisitionDto, InquisitionApi>() {

    override val route: String
        get() = "/api/inquisition"

    override fun getDto(): InquisitionDto = DtoTestInstanceFactory.inquisitionDto()

    override val serializer: KSerializer<InquisitionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<InquisitionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, InquisitionDto>): InquisitionApi {
        TODO("Not yet implemented")
    }

}