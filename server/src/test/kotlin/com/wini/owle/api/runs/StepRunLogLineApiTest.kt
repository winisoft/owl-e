package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.step.StepRunLogLineApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.step.StepRunLogLine
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class StepRunLogLineApiTest : BaseLongPkApiTest<StepRunLogLine, StepRunLogLineApi>() {

    override val route: String = "/api/execution-log"

    override fun getDto(id: Long): StepRunLogLine = DtoTestInstanceFactory.executionLogDto(
        id = id
    )

    override val serializer: KSerializer<StepRunLogLine> = StepRunLogLine.serializer()
    
    override val listSerializer: KSerializer<List<StepRunLogLine>> = ListSerializer(StepRunLogLine.serializer())

    override fun getApiInstance(
        dll: DLL<Long, StepRunLogLine>,
        validator: BaseValidator<StepRunLogLine>
    ): StepRunLogLineApi = StepRunLogLineApi(dll, validator)

}