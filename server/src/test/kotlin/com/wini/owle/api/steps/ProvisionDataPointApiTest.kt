package com.wini.owle.api.steps

import com.wini.owle.api.ApiError
import com.wini.owle.api.ApiResponse
import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.api.TestValidators
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.ProvisionDataPoints
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import com.wini.owle.validators.DtoValidator
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


open class ProvisionDataPointApiTest : BaseCompositePkApiTest<ProvisionDataPointDto, ProvisionDataPointApi>() {

    override val route: String = "/api/provision-datapoint"

    override val malformedParamsRoute: String
        get() = "$route/id?provisionId=Text&dataPointId=Text"

    override fun getDto(id1: Long, id2: Long): ProvisionDataPointDto = DtoTestInstanceFactory.provisionDataPointDto(
        provision = DtoTestInstanceFactory.provisionDto(id = id1),
        dataPoint = DtoTestInstanceFactory.dataPointDto(id = id2)
    )

    override fun compositeId1(): CompositeID {
        return CompositeID {
            it[ProvisionDataPoints.provision] = 1
            it[ProvisionDataPoints.dataPoint] = 1
        }
    }

    override fun compositeId2(): CompositeID {
        return CompositeID {
            it[ProvisionDataPoints.provision] = 2
            it[ProvisionDataPoints.dataPoint] = 2
        }
    }

    override fun improperParameters(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("provisionId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("dataPointId"))
        )
    }

    override fun missingParamErrors(): List<ApiError> {
        return listOf(
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("provisionId")),
            ApiError.fromAsyncError(AsyncError.ApiRequestError.MissingParameter("dataPointId"))
        )
    }

    override val serializer: KSerializer<ProvisionDataPointDto> = ProvisionDataPointDto.serializer()
    
    override val listSerializer: KSerializer<List<ProvisionDataPointDto>> = ListSerializer(ProvisionDataPointDto.serializer())

    override fun getApiInstance(
        dll: DLL<CompositeID, ProvisionDataPointDto>,
        validator: DtoValidator<ProvisionDataPointDto>
    ): ProvisionDataPointApi = ProvisionDataPointApi(dll, validator)

}