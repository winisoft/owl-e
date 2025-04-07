package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.RevisionDto
import kotlinx.serialization.KSerializer


class RevisionApiTest : BaseLongPkApiTest<Long, RevisionDto, RevisionApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): RevisionDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<RevisionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<RevisionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, RevisionDto>): RevisionApi {
        TODO("Not yet implemented")
    }

}