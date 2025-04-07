// server/src/test/kotlin/com/wini/owle/api/runs/ProvisionDataPointApiTest.kt
package com.wini.owle.api.runs

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ProvisionDataPointDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID

open class ProvisionDataPointApiTest : BaseCompositePkApiTest<ProvisionDataPointDto, ProvisionDataPointApi>() {
    override val route: String = "/api/provision-datapoint"

    override fun getDto(): ProvisionDataPointDto = DtoTestInstanceFactory.provisionDataPointDto()

    override val serializer: KSerializer<ProvisionDataPointDto> = ProvisionDataPointDto.serializer()
    
    override val listSerializer: KSerializer<List<ProvisionDataPointDto>> = ListSerializer(ProvisionDataPointDto.serializer())

    override fun getApiInstance(dll: DLL<CompositeID, ProvisionDataPointDto>): ProvisionDataPointApi = ProvisionDataPointApi(dll)
}