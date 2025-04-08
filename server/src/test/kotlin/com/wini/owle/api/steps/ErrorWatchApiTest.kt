package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ErrorWatchDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class ErrorWatchApiTest : BaseLongPkApiTest<ErrorWatchDto, ErrorWatchApi>() {

    override val route: String = "/api/error-watch"

    override fun getDto(): ErrorWatchDto = DtoTestInstanceFactory.errorWatchDto()

    override val serializer: KSerializer<ErrorWatchDto> = ErrorWatchDto.serializer()
    
    override val listSerializer: KSerializer<List<ErrorWatchDto>> = ListSerializer(ErrorWatchDto.serializer())

    override fun getApiInstance(
        
        dll: DLL<Long, ErrorWatchDto>,
        validator: (ErrorWatchDto) -> Boolean
    ): ErrorWatchApi = ErrorWatchApi(dll, validator)

}