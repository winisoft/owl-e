package com.wini.owle.api.runs

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ProvisionDataPointDto
import kotlinx.serialization.KSerializer
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointApiTest : BaseCompositePkApiTest<CompositeID, ProvisionDataPointDto, ProvisionDataPointApi>() {
    override val route: String
        get() = "/api/provision-datapoint"

    override fun getDto(): ProvisionDataPointDto = DtoTestInstanceFactory.provisionDataPointDto()

    override val serializer: KSerializer<ProvisionDataPointDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ProvisionDataPointDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<CompositeID, ProvisionDataPointDto>): ProvisionDataPointApi {
        TODO("Not yet implemented")
    }

}