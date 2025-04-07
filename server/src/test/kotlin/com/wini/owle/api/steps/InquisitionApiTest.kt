// server/src/test/kotlin/com/wini/owle/api/steps/InquisitionApiTest.kt
package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.InquisitionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

open class InquisitionApiTest : BaseLongPkApiTest<InquisitionDto, InquisitionApi>() {
    override val route: String = "/api/inquisition"

    override fun getDto(): InquisitionDto = DtoTestInstanceFactory.inquisitionDto()

    override val serializer: KSerializer<InquisitionDto> = InquisitionDto.serializer()
    
    override val listSerializer: KSerializer<List<InquisitionDto>> = ListSerializer(InquisitionDto.serializer())

    override fun getApiInstance(dll: DLL<Long, InquisitionDto>): InquisitionApi = InquisitionApi(dll)
}