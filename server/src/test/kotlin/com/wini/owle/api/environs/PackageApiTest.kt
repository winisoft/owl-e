package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.PackageDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class PackageApiTest : BaseLongPkApiTest<Long, PackageDto, PackageApi>() {

    override val route: String = "/api/package"
    
    override fun getDto(): PackageDto = DtoTestInstanceFactory.packageDto()
    
    override val serializer: KSerializer<PackageDto> = PackageDto.serializer()
    
    override val listSerializer: KSerializer<List<PackageDto>> = ListSerializer(PackageDto.serializer())
    
    override fun getApiInstance(dll: DLL<Long, PackageDto>): PackageApi = PackageApi(dll)
}