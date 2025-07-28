package com.wini.owle.api.steps

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Inquisition
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class InquisitionEntityApiTest : BaseLongPkApiTest<Inquisition, InquisitionApi>() {

    override val route: String = "/api/inquisition"

    override fun getDto(id: Long): Inquisition = DtoTestInstanceFactory.inquisitionDto(
        id = id
    )

    override val serializer: KSerializer<Inquisition> = Inquisition.serializer()
    
    override val listSerializer: KSerializer<List<Inquisition>> = ListSerializer(Inquisition.serializer())

    override fun getApiInstance(
        dll: DLL<Long, Inquisition>,
        validator: DtoValidator<Inquisition>
    ): InquisitionApi = InquisitionApi(dll, validator)

}