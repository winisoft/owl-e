package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.DataPointDto
import kotlinx.serialization.KSerializer

class DataPointsApiTest : BaseLongPkApiTest<Long, DataPointDto, DataPointsApi>() {

    override val route: String
        get() = "/api/data-points"

    override fun getDto(): DataPointDto = DtoTestInstanceFactory.dataPointDto()

    override val serializer: KSerializer<DataPointDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<DataPointDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, DataPointDto>): DataPointsApi {
        TODO("Not yet implemented")
    }

}