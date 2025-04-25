package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.InitialDataPointInjector
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PreReqInjectionApiTest : BaseLongPkApiTest<InitialDataPointInjector, PreReqInjectionApi>() {

    override val route: String = "/api/prereq-injection"

    override fun getDto(id: Long): InitialDataPointInjector = DtoTestInstanceFactory.preReqInjectionDto(
        id = id
    )

    override val serializer: KSerializer<InitialDataPointInjector> = InitialDataPointInjector.serializer()
    
    override val listSerializer: KSerializer<List<InitialDataPointInjector>> = ListSerializer(InitialDataPointInjector.serializer())

    override fun getApiInstance(
        dll: DLL<Long, InitialDataPointInjector>,
        validator: BaseValidator<InitialDataPointInjector>
    ): PreReqInjectionApi = PreReqInjectionApi(dll, validator)

}