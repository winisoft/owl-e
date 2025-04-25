package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.step.StepRunApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.step.StepExecution
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class StepRunApiTest : BaseLongPkApiTest<StepExecution, StepRunApi>() {

    override val route: String = "/api/step-execution"

    override fun getDto(id: Long): StepExecution = DtoTestInstanceFactory.stepExecutionDto(
        id = id
    )

    override val serializer: KSerializer<StepExecution> = StepExecution.serializer()
    
    override val listSerializer: KSerializer<List<StepExecution>> = ListSerializer(StepExecution.serializer())

    override fun getApiInstance(
        dll: DLL<Long, StepExecution>,
        validator: BaseValidator<StepExecution>
    ): StepRunApi = StepRunApi(dll, validator)

}