package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ProvisionDto
import kotlinx.serialization.KSerializer


class ProvisionApiTest : BaseLongPkApiTest<Long, ProvisionDto, ProvisionApi>() {

    override val route: String
        get() = "/api/provision"

    override fun getDto(): ProvisionDto = DtoTestInstanceFactory.provisionDto()

    override val serializer: KSerializer<ProvisionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ProvisionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, ProvisionDto>): ProvisionApi {
        TODO("Not yet implemented")
    }

}