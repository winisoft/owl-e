package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.PackageDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PackageApiTest : BaseLongPkApiTest<PackageDto, PackageApi>() {

    override val route: String = "/api/package"
    
    override fun getDto(id: Long): PackageDto = DtoTestInstanceFactory.packageDto(
        id = id
    )
    
    override val serializer: KSerializer<PackageDto> = PackageDto.serializer()
    
    override val listSerializer: KSerializer<List<PackageDto>> = ListSerializer(PackageDto.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, PackageDto>,
        validator: BaseValidator<PackageDto>
    ): PackageApi = PackageApi(dll, validator)

}