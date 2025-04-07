package com.wini.owle.api.environs

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import kotlinx.serialization.KSerializer
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyApiTest : BaseCompositePkApiTest<CompositeID, ToolPackageDependencyDto, ToolPackageDependencyApi>() {

    override val route: String = "/api/tool-package-dep"

    override fun getDto(): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto()

    override val serializer: KSerializer<ToolPackageDependencyDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<ToolPackageDependencyDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<CompositeID, ToolPackageDependencyDto>): ToolPackageDependencyApi {
        TODO("Not yet implemented")
    }

}

