package com.wini.owle.api.environs


import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.StringIdEndpointImpl
import com.wini.owle.api.ULongIdEndpointImpl
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

}