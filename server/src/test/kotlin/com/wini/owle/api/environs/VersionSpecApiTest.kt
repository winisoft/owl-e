package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.VersionSpec
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class VersionSpecApiTest : BaseLongPkApiTest<VersionSpec, VersionSpecApi>() {

    override val route: String = "/api/version-spec"

    override fun getDto(id: Long): VersionSpec = DtoTestInstanceFactory.versionSpecDto(
        id = id
    )

    override val serializer: KSerializer<VersionSpec> = VersionSpec.serializer()
    
    override val listSerializer: KSerializer<List<VersionSpec>> = ListSerializer(VersionSpec.serializer())

    override fun getApiInstance(
        dll: DLL<Long, VersionSpec>,
        validator: DtoValidator<VersionSpec>
    ): VersionSpecApi = VersionSpecApi(dll, validator)

}