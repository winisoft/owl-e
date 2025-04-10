package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.StepExecutionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class StepExecutionApiTest : BaseLongPkApiTest<StepExecutionDto, StepExecutionApi>() {

    override val route: String = "/api/step-execution"

    override fun getDto(id: Long): StepExecutionDto = DtoTestInstanceFactory.stepExecutionDto(
        id = id
    )

    override val serializer: KSerializer<StepExecutionDto> = StepExecutionDto.serializer()
    
    override val listSerializer: KSerializer<List<StepExecutionDto>> = ListSerializer(StepExecutionDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, StepExecutionDto>,
        validator: DtoValidator<StepExecutionDto>
    ): StepExecutionApi = StepExecutionApi(dll, validator)

}