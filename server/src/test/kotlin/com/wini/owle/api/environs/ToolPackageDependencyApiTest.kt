package com.wini.owle.api.environs

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID


open class ToolPackageDependencyApiTest : BaseCompositePkApiTest<ToolPackageDependencyDto, ToolPackageDependencyApi>() {

    override val route: String = "/api/tool-package-dep"

    override fun getDto(): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto()

    override val serializer: KSerializer<ToolPackageDependencyDto> = ToolPackageDependencyDto.serializer()

    override val listSerializer: KSerializer<List<ToolPackageDependencyDto>> = ListSerializer(ToolPackageDependencyDto.serializer())

    override fun getApiInstance(dll: DLL<CompositeID, ToolPackageDependencyDto>): ToolPackageDependencyApi = ToolPackageDependencyApi(dll)

}

