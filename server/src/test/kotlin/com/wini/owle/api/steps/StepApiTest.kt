package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Step
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class StepApiTest : BaseLongPkApiTest<Step, StepApi>() {

    override val route: String = "/api/step"

    override fun getDto(id: Long): Step = DtoTestInstanceFactory.stepDto(
        id = id
    )

    override val serializer: KSerializer<Step> = Step.serializer()
    
    override val listSerializer: KSerializer<List<Step>> = ListSerializer(Step.serializer())

    override fun getApiInstance(
        dll: DLL<Long, Step>,
        validator: DtoValidator<Step>
    ): StepApi = StepApi(dll, validator)

}