package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.PreReqInjectionDto
import kotlinx.serialization.KSerializer


class PreReqInjectionApiTest : BaseLongPkApiTest<Long, PreReqInjectionDto, PreReqInjectionApi>() {

    override val route: String
        get() ="/api/prereq-injection"

    override fun getDto(): PreReqInjectionDto = DtoTestInstanceFactory.preReqInjectionDto()

    override val serializer: KSerializer<PreReqInjectionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<PreReqInjectionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, PreReqInjectionDto>): PreReqInjectionApi {
        TODO("Not yet implemented")
    }

}