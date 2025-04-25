package com.wini.owle.api.workflows

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.workflows.Phase
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PhaseApiTest : BaseLongPkApiTest<Phase, PhaseApi>() {

    override val route: String = "/api/phase"

    override fun getDto(id: Long): Phase = DtoTestInstanceFactory.phaseDto(
        id = id
    )

    override val serializer: KSerializer<Phase> = Phase.serializer()
    
    override val listSerializer: KSerializer<List<Phase>> = ListSerializer(Phase.serializer())

    override fun getApiInstance(
        dll: DLL<Long, Phase>,
        validator: BaseValidator<Phase>
    ): PhaseApi = PhaseApi(dll, validator)

}