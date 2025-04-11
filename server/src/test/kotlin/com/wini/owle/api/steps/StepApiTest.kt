package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.StepDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class StepApiTest : BaseLongPkApiTest<StepDto, StepApi>() {

    override val route: String = "/api/step"

    override fun getDto(id: Long): StepDto = DtoTestInstanceFactory.stepDto(
        id = id
    )

    override val serializer: KSerializer<StepDto> = StepDto.serializer()
    
    override val listSerializer: KSerializer<List<StepDto>> = ListSerializer(StepDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, StepDto>,
        validator: BaseValidator<StepDto>
    ): StepApi = StepApi(dll, validator)

}