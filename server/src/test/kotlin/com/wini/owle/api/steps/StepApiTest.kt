package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.StepDto
import kotlinx.serialization.KSerializer

class StepApiTest : BaseLongPkApiTest<Long, StepDto, StepApi>() {
    override val route: String
        get() = "/api/step"

    override fun getDto(): StepDto = DtoTestInstanceFactory.stepDto()

    override val serializer: KSerializer<StepDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<StepDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, StepDto>): StepApi {
        TODO("Not yet implemented")
    }

}