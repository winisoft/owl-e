package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPointDto
import kotlinx.serialization.KSerializer

class DataPointsApiTest : BaseLongPkApiTest<Long, DataPointDto, DataPointsApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): DataPointDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<DataPointDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<DataPointDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, DataPointDto>): DataPointsApi {
        TODO("Not yet implemented")
    }

}