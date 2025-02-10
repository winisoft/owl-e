package com.wini.owle.air.ktor.feature.jitter.processors

import io.ktor.server.application.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Duration
import java.util.*
import com.wini.owle.air.domain.model.item.Call
import com.wini.owle.air.ktor.feature.jitter.ResponseProcessingStrategy
import kotlinx.serialization.Serializable

interface RequestInfoProcessor: ResponseProcessingStrategy {

    override fun execute(applicationCall: ApplicationCall, call: Call): String? {
        val requestInfo: RequestInfo = Json.decodeFromString(call.responseBody?.value?.toString() ?: return null)
        call.queryParams?.value?.get("code").toString().toInt().apply {
            when (this) {
                in 0..2, 4, 5, in 7..10 ->
                    RequestInfo.ServiceRequest.etaTime = Date().time.plus(Duration.ofMinutes(30).toMillis()).toString()
                6, 12 ->
                    RequestInfo.ServiceRequest.etaTime = ""
                else -> {
                }
            }
            if (this == 9 || this == 10)
                RequestInfo.ServiceRequest.predictedEtaTime =
                    Date().time.plus(Duration.ofMinutes(20).toMillis()).toString()
            if (this == 9)
                RequestInfo.ServiceRequest.updatedEtaTime =
                    Date().time.plus(Duration.ofMinutes(10).toMillis()).toString()
        }
        return Json.encodeToString(requestInfo)
    }

    @Serializable
    object RequestInfo {
        var id: String? = ""
        var type: String? = ""
        var purchaseOrderNumber: String? = ""
        var eta: String? = ""
        var etaTime: String? = ""
        var serviceRequest: ServiceRequest? = null
        var provider: Provider? = null
        var status: String? = ""

        @Serializable
        object ServiceRequest {
            var consumerFirstName: String? = ""
            var consumerLastName: String? = ""
            var consumerVehicleMake: String? = ""
            var consumerVehicleModel: String? = ""
            var serviceType: String? = ""
            var cost: String? = ""
            var status: String? = ""
            var serviceCreationTime: String? = ""
            var incidentAddress: Address? = null
            var etaTime: String? = ""
            var updatedEtaTime: String? = ""
            var predictedEtaTime: String? = ""
        }

        @Serializable
        object Address {
            var addressLine1: String? = null
            var addressLine2: String? = null
            var city: String? = null
            var latitude: Float? = null
            var longitude: Float? = null
            var state: String? = null
            var country: String? = null
            var zipCode: String? = null
        }

        @Serializable
        object Provider {
            val companyName: String? = null
            val contactName: String? = null
            val contactNumber: String? = null
            val providerAddress: Address? = null
        }
    }

}