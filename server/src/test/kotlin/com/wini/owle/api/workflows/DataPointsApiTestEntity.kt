package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPoint
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class DataPointsApiTestEntity : BaseLongPkApiTest<DataPoint, DataPointsApi>() {

    override val route: String = "/api/data-points"

    override fun getDto(id: Long): DataPoint = DtoTestInstanceFactory.dataPointDto(
        id = id
    )

    override val serializer: KSerializer<DataPoint> = DataPoint.serializer()
    
    override val listSerializer: KSerializer<List<DataPoint>> = ListSerializer(DataPoint.serializer())

    override fun getApiInstance(
        dll: DLL<Long, DataPoint>,
        validator: DtoValidator<DataPoint>
    ): DataPointsApi = DataPointsApi(dll, validator)
}