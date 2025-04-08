package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.DataPointDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

open class DataPointsApiTest : BaseLongPkApiTest<DataPointDto, DataPointsApi>() {

    override val route: String = "/api/data-points"

    override fun getDto(): DataPointDto = DtoTestInstanceFactory.dataPointDto()

    override val serializer: KSerializer<DataPointDto> = DataPointDto.serializer()
    
    override val listSerializer: KSerializer<List<DataPointDto>> = ListSerializer(DataPointDto.serializer())

    override fun getApiInstance(dll: DLL<Long, DataPointDto>): DataPointsApi = DataPointsApi(dll)
}