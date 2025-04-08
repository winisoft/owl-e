package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.PackageRepoDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class PackageRepoApiTest : BaseLongPkApiTest<PackageRepoDto, PackageRepoApi>() {

    override val route: String = "/api/package-repo"

    override fun getDto(): PackageRepoDto = DtoTestInstanceFactory.packageRepoDto()

    override val serializer: KSerializer<PackageRepoDto> = PackageRepoDto.serializer()
    
    override val listSerializer: KSerializer<List<PackageRepoDto>> = ListSerializer(PackageRepoDto.serializer())

    override fun getApiInstance(dll: DLL<Long, PackageRepoDto>): PackageRepoApi = PackageRepoApi(dll)

}