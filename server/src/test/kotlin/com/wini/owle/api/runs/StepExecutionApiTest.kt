package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.StepExecutionDto
import kotlinx.serialization.KSerializer


class StepExecutionApiTest : BaseLongPkApiTest<Long, StepExecutionDto, StepExecutionApi>() {
    override val route: String
        get() = "/api/step-execution"

    override fun getDto(): StepExecutionDto = DtoTestInstanceFactory.stepExecutionDto()

    override val serializer: KSerializer<StepExecutionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<StepExecutionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, StepExecutionDto>): StepExecutionApi {
        TODO("Not yet implemented")
    }

}