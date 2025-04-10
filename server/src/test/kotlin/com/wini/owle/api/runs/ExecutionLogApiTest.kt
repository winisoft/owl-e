package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.ExecutionLogDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class ExecutionLogApiTest : BaseLongPkApiTest<ExecutionLogDto, ExecutionLogApi>() {

    override val route: String = "/api/execution-log"

    override fun getDto(id: Long): ExecutionLogDto = DtoTestInstanceFactory.executionLogDto(
        id = id
    )

    override val serializer: KSerializer<ExecutionLogDto> = ExecutionLogDto.serializer()
    
    override val listSerializer: KSerializer<List<ExecutionLogDto>> = ListSerializer(ExecutionLogDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, ExecutionLogDto>,
        validator: DtoValidator<ExecutionLogDto>
    ): ExecutionLogApi = ExecutionLogApi(dll, validator)

}