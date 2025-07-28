package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Provision
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class ProvisionApiTest : BaseLongPkApiTest<Provision, ProvisionApi>() {

    override val route: String = "/api/provision"

    override fun getDto(id: Long): Provision = DtoTestInstanceFactory.provisionDto(
        id = id
    )

    override val serializer: KSerializer<Provision> = Provision.serializer()
    
    override val listSerializer: KSerializer<List<Provision>> = ListSerializer(Provision.serializer())

    override fun getApiInstance(
        dll: DLL<Long, Provision>,
        validator: DtoValidator<Provision>
    ): ProvisionApi = ProvisionApi(dll, validator)

}