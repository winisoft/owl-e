package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.PhaseDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class PhaseApiTest : BaseLongPkApiTest<PhaseDto, PhaseApi>() {

    override val route: String = "/api/phase"

    override fun getDto(): PhaseDto = DtoTestInstanceFactory.phaseDto()

    override val serializer: KSerializer<PhaseDto> = PhaseDto.serializer()
    
    override val listSerializer: KSerializer<List<PhaseDto>> = ListSerializer(PhaseDto.serializer())

    override fun getApiInstance(dll: DLL<Long, PhaseDto>): PhaseApi = PhaseApi(dll)

}