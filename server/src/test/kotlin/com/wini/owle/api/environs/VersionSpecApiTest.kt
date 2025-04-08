package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.VersionSpecDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class VersionSpecApiTest : BaseLongPkApiTest<VersionSpecDto, VersionSpecApi>() {

    override val route: String = "/api/version-spec"

    override fun getDto(): VersionSpecDto = DtoTestInstanceFactory.versionSpecDto()

    override val serializer: KSerializer<VersionSpecDto> = VersionSpecDto.serializer()
    
    override val listSerializer: KSerializer<List<VersionSpecDto>> = ListSerializer(VersionSpecDto.serializer())

    override fun getApiInstance(dll: DLL<Long, VersionSpecDto>): VersionSpecApi = VersionSpecApi(dll)

}