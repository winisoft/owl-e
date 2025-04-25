package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ErrorWatchFn
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class ErrorWatchEntityFnApiTest : BaseLongPkApiTest<ErrorWatchFn, ErrorWatchFnApi>() {

    override val route: String = "api/error-watch-fn"

    override fun getDto(id: Long): ErrorWatchFn = DtoTestInstanceFactory.errorWatchFnDto(
        id = id
    )

    override val serializer: KSerializer<ErrorWatchFn> = ErrorWatchFn.serializer()
    
    override val listSerializer: KSerializer<List<ErrorWatchFn>> = ListSerializer(ErrorWatchFn.serializer())

    override fun getApiInstance(
        dll: DLL<Long, ErrorWatchFn>,
        validator: BaseValidator<ErrorWatchFn>
    ): ErrorWatchFnApi = ErrorWatchFnApi(dll, validator)

}