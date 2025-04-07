package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.PhaseDto
import kotlinx.serialization.KSerializer


class PhaseApiTest : BaseLongPkApiTest<Long, PhaseDto, PhaseApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): PhaseDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<PhaseDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<PhaseDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, PhaseDto>): PhaseApi {
        TODO("Not yet implemented")
    }

}