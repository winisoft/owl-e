package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.step.StepRunApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.step.StepRun
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class StepRunApiTest : BaseLongPkApiTest<StepRun, StepRunApi>() {

    override val route: String = "/api/step-execution"

    override fun getDto(id: Long): StepRun = DtoTestInstanceFactory.stepExecutionDto(
        id = id
    )

    override val serializer: KSerializer<StepRun> = StepRun.serializer()
    
    override val listSerializer: KSerializer<List<StepRun>> = ListSerializer(StepRun.serializer())

    override fun getApiInstance(
        dll: DLL<Long, StepRun>,
        validator: DtoValidator<StepRun>
    ): StepRunApi = StepRunApi(dll, validator)

}