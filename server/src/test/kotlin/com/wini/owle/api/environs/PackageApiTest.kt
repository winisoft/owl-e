package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageDto
import kotlinx.serialization.KSerializer


class PackageApiTest: BaseLongPkApiTest<Long, PackageDto, PackageApi>() {

    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): PackageDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<PackageDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<PackageDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, PackageDto>): PackageApi {
        TODO("Not yet implemented")
    }


}