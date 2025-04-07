package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.VersionSpecDto
import kotlinx.serialization.KSerializer


class VersionSpecApiTest : BaseLongPkApiTest<Long, VersionSpecDto, VersionSpecApi>() {
    override val route: String
        get() = "/api/version-spec"

    override fun getDto(): VersionSpecDto = DtoTestInstanceFactory.versionSpecDto()

    override val serializer: KSerializer<VersionSpecDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<VersionSpecDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, VersionSpecDto>): VersionSpecApi {
        TODO("Not yet implemented")
    }


}