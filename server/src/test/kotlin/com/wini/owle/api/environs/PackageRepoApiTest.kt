package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.PackageRepoDto
import kotlinx.serialization.KSerializer


class PackageRepoApiTest : BaseLongPkApiTest<Long, PackageRepoDto, PackageRepoApi>() {

    override val route: String = "/api/package-repo"

    override fun getDto(): PackageRepoDto = DtoTestInstanceFactory.packageRepoDto()

    override val serializer: KSerializer<PackageRepoDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<PackageRepoDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, PackageRepoDto>): PackageRepoApi {
        TODO("Not yet implemented")
    }


}