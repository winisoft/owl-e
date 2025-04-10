package com.wini.owle.api.environs

import com.wini.owle.api.ApiError
import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTest
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.ProvisionDataPoints
import com.wini.owle.entity.ToolPackageDependencies
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.environs.ToolPackageDependencyValidator
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


open class ToolPackageDependencyApiTest : BaseCompositePkApiTest<ToolPackageDependencyDto, ToolPackageDependencyApi>() {

    override val route: String = "/api/tool-package-dep"

    override fun getDto(id1: Long, id2: Long): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto()

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

    override fun improperParameters(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("toolId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("packageId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("versionSpecId")),
        )
    }

    override fun missingParamErrors(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("provisionId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("dataPointId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("versionSpecId"))
        )
    }

    fun get3IdDto(
        id1: Long,
        id2: Long,
        id3: Long
    ): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto(
        toolId = id1,
        packageId = id2,
        versionSpecId = id3
    )

    override val getByIdRoute: String
        get() {
            val dto = get3IdDto(1, 1, 1)
            return "$route/id?toolId=${dto.toolId}&packageId=${dto.packageId}&versionSpecId=${dto.versionSpecId}"
        }

    override val deleteByIdRoute: String
        get() {
            val dto = get3IdDto(1, 1, 1)
            return "$route/tool-package-dep/id?toolId=${dto.toolId}&packageId=${dto.packageId}&versionSpecId=${dto.versionSpecId}"
        }

    override val invalidIdRoute: String
        get() = "$route/id?missingParams=true"

    override val malformedParamsRoute: String
        get() = "$route/tool-package-dep/id?toolId=Text&packageId=0.0&versionSpecId= "

    fun getDto(id1: Long, id2: Long, id3: Long): ToolPackageDependencyDto = DtoTestInstanceFactory.toolPackageDependencyDto(
        toolId = id1, packageId = id2, versionSpecId = id3
    )

    override val serializer: KSerializer<ToolPackageDependencyDto> = kotlinx.serialization.serializer()

    override val listSerializer: KSerializer<List<ToolPackageDependencyDto>> = ListSerializer(serializer)

    override fun getApiInstance(
        dll: DLL<CompositeID, ToolPackageDependencyDto>,
        validator: DtoValidator<ToolPackageDependencyDto>
    ): ToolPackageDependencyApi = ToolPackageDependencyApi(dll, validator)


}

