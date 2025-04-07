// server/src/test/kotlin/com/wini/owle/api/steps/StepApiTest.kt
package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.StepDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

open class StepApiTest : BaseLongPkApiTest<StepDto, StepApi>() {
    override val route: String = "/api/step"

    override fun getDto(): StepDto = DtoTestInstanceFactory.stepDto()

    override val serializer: KSerializer<StepDto> = StepDto.serializer()
    
    override val listSerializer: KSerializer<List<StepDto>> = ListSerializer(StepDto.serializer())

    override fun getApiInstance(dll: DLL<Long, StepDto>): StepApi = StepApi(dll)
}