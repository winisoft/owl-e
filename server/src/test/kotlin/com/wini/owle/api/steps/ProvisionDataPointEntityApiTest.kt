package com.wini.owle.api.steps

import com.wini.owle.api.Base2IdCompositeApiTest
import com.wini.owle.error.OwleError
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.entity.ProvisionDataPoints
import com.wini.owle.entity.composteid.CompositeIdFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.validators.BaseValidator


open class ProvisionDataPointEntityApiTest : Base2IdCompositeApiTest<ProvisionDataPoint, ProvisionDataPointApi>() {

    override val route: String = "/api/provision-datapoint"

    override fun getDto(id1: Long, id2: Long): ProvisionDataPoint = DtoTestInstanceFactory.provisionDataPointDto(
        provision = DtoTestInstanceFactory.provisionDto(id = id1),
        dataPoint = DtoTestInstanceFactory.dataPointDto(id = id2)
    )

    override fun getApiInstance(
        dll: DLL<CompositeID, ProvisionDataPoint>,
        validator: BaseValidator<ProvisionDataPoint>,
        compositeIdFactory: CompositeIdFactory<ProvisionDataPoint>
    ): ProvisionDataPointApi {
        return ProvisionDataPointApi(
            dll = dll,
            validator = validator,
            compositeIdFactory = compositeIdFactory
        )
    }

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

    override fun allParamsMalformedErrors(): List<OwleError.ApiRequestError> {
        return listOf(
            OwleError.ApiRequestError.MissingParameter("provisionId"),
            OwleError.ApiRequestError.MissingParameter("dataPointId")
        )
    }

    override fun onlyFirstParamProvidedErrors(): List<OwleError.ApiRequestError> {
        return listOf(
            OwleError.ApiRequestError.MissingParameter("dataPointId")
        )
    }

    override val malformedParamsRoute: String
        get() = "$route/id?provisionId=Text&dataPointId=Text"

    override val getByIdRoute: String
        get() = getDto(1, 1).run {
        "$route/id?provisionId=$provisionId&dataPointId=$dataPointId"
    }

    override val deleteByIdRoute: String
        get() = getDto(1, 1).run {
            "$route/id?provisionId=$provisionId}&dataPointId={$dataPointId"
        }

    override val onlyFirstParamRoute: String
        get() = getDto(1, 1).run {
            "$route/id?provisionId=$provisionId"
        }

    override val serializer: KSerializer<ProvisionDataPoint> = ProvisionDataPoint.serializer()
    
    override val listSerializer: KSerializer<List<ProvisionDataPoint>> = ListSerializer(ProvisionDataPoint.serializer())

}