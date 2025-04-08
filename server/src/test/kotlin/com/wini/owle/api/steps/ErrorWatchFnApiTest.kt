package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ErrorWatchFnDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class ErrorWatchFnApiTest : BaseLongPkApiTest<ErrorWatchFnDto, ErrorWatchFnApi>() {

    override val route: String = "api/error-watch-fn"

    override fun getDto(): ErrorWatchFnDto = DtoTestInstanceFactory.errorWatchFnDto()

    override val serializer: KSerializer<ErrorWatchFnDto> = ErrorWatchFnDto.serializer()
    
    override val listSerializer: KSerializer<List<ErrorWatchFnDto>> = ListSerializer(ErrorWatchFnDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, ErrorWatchFnDto>,
        validator: (ErrorWatchFnDto) -> Boolean
    ): ErrorWatchFnApi = ErrorWatchFnApi(dll, validator)

}