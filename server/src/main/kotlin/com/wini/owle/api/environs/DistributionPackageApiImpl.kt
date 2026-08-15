package com.wini.owle.api.environs


import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.StringIdEndpointImpl
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.api.environs.pkg.DistributionPackageApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.validators.environs.pkg.DistributionPackageValidator
import io.ktor.server.application.call
import kotlinx.serialization.builtins.serializer

class DistributionPackageApiImpl(
    dll: DLL<String, DistributionPackage>,
    validator: DistributionPackageValidator,
    override val endpointPath: String
): StringIdEndpointImpl<DistributionPackage>(
    dll = dll,
    serializer = DistributionPackage.serializer(),
    endpointPath = endpointPath,
    validator = validator
), DistributionPackageApi  {

    override fun register(routing: Routing) {
        routing.apply {

            get("/api/package") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }

            get("/api/package/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }

            post("/api/package") {
                postMany { postMany { call.execute(OwleApiCall.CreateOne) } }
            }
            post("/api/package/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/package") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/package/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/package/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/package/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }

}