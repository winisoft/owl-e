package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.PackageRepoDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PackageRepoApiTest : BaseLongPkApiTest<PackageRepoDto, PackageRepoApi>() {

    override val route: String = "/api/package-repo"

    override fun getDto(id: Long): PackageRepoDto = DtoTestInstanceFactory.packageRepoDto(
        id = id
    )

    override val serializer: KSerializer<PackageRepoDto> = PackageRepoDto.serializer()
    
    override val listSerializer: KSerializer<List<PackageRepoDto>> = ListSerializer(PackageRepoDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, PackageRepoDto>,
        validator: BaseValidator<PackageRepoDto>
    ): PackageRepoApi = PackageRepoApi(dll, validator)

}