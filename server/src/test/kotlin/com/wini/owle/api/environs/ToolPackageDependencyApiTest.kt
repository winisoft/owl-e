package com.wini.owle.api.environs

import com.wini.owle.api.Base3IdCompositeApiTest
import com.wini.owle.validators.BaseValidator
import com.wini.owle.async.AsyncError
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.ToolPackageDependencies
import com.wini.owle.entity.composteid.CompositeIdFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID


open class ToolPackageDependencyApiTest : Base3IdCompositeApiTest<ToolPackageDependencyDto, ToolPackageDependencyApi>() {

    final override fun getDto(
        id1: Long,
        id2: Long,
        id3: Long
    ): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto(
        toolId = id1,
        packageId = id2,
        versionSpecId = id3
    )

    override fun getApiInstance(
        dll: DLL<CompositeID, ToolPackageDependencyDto>,
        validator: BaseValidator<ToolPackageDependencyDto>,
        compositeIdFactory: CompositeIdFactory<ToolPackageDependencyDto>
    ): ToolPackageDependencyApi {
        return ToolPackageDependencyApi(
            dll = dll,
            validator = validator,
            compositeIdFactory = compositeIdFactory
        )
    }

    override fun compositeId1(): CompositeID {
        return CompositeID {
            it[ToolPackageDependencies.tool] = 1
            it[ToolPackageDependencies.packageEntity] = 1
            it[ToolPackageDependencies.versionSpec] = 1
        }
    }

    override fun compositeId2(): CompositeID {
        return CompositeID {
            it[ToolPackageDependencies.tool] = 2
            it[ToolPackageDependencies.packageEntity] = 2
            it[ToolPackageDependencies.versionSpec] = 2
        }
    }

    override fun allParamsMalformedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.MissingParameter("toolId"),
            AsyncError.ApiRequestError.MissingParameter("packageId"),
            AsyncError.ApiRequestError.MissingParameter("versionSpecId"),
        )
    }

    override fun onlyFirstParamProvidedErrors(): List<AsyncError.ApiRequestError> {
        return listOf(
            AsyncError.ApiRequestError.MissingParameter("dataPointId"),
            AsyncError.ApiRequestError.MissingParameter("versionSpecId")
        )
    }

    override val route: String = "/api/tool-package-dep"

    override val getByIdRoute: String = getDto(1, 1, 1).run {
        "$route/id?toolId=${toolId}&packageId=${packageId}&versionSpecId=${versionSpecId}"
    }


    override val deleteByIdRoute: String
        get() {
            val dto = getDto(1, 1, 1)
            return "$route/id?toolId=${dto.toolId}&packageId=${dto.packageId}&versionSpecId=${dto.versionSpecId}"
        }

    override val onlyFirstParamRoute: String
        get() = getDto(1, 1, 1).run { "$route/id?toolId=${toolId}" }

    override val malformedParamsRoute: String
        get() = "$route/id?toolId=Text&packageId=0.0&versionSpecId=%20"


    final override val serializer: KSerializer<ToolPackageDependencyDto> = kotlinx.serialization.serializer()

    override val listSerializer: KSerializer<List<ToolPackageDependencyDto>> = ListSerializer(serializer)


}

