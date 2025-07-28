package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class ErrorWatchEntityApiTest : BaseLongPkApiTest<ErrorWatch, ErrorWatchApi>() {

    override val route: String = "/api/error-watch"

    override fun getDto(id: Long): ErrorWatch = DtoTestInstanceFactory.errorWatchDto(
        id = id
    )

    override val serializer: KSerializer<ErrorWatch> = ErrorWatch.serializer()
    
    override val listSerializer: KSerializer<List<ErrorWatch>> = ListSerializer(ErrorWatch.serializer())

    override fun getApiInstance(

        dll: DLL<Long, ErrorWatch>,
        validator: DtoValidator<ErrorWatch>
    ): ErrorWatchApi = ErrorWatchApi(dll, validator)

}