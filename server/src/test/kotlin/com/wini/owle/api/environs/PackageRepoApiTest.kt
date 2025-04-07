package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageRepoDto
import kotlinx.serialization.KSerializer


class PackageRepoApiTest : BaseLongPkApiTest<Long, PackageRepoDto, PackageRepoApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): PackageRepoDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<PackageRepoDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<PackageRepoDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, PackageRepoDto>): PackageRepoApi {
        TODO("Not yet implemented")
    }


}