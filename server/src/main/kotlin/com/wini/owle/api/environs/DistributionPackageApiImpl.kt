package com.wini.owle.api.environs

import com.wini.owle.api.StringIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.validators.environs.pkg.DistributionPackageValidator
import io.ktor.server.routing.*


class DistributionPackageApiImpl(
    dll: DLL<String, DistributionPackage>,
    validator: DistributionPackageValidator,
    override val endpointPath: String
): StringIdEndpointImpl<DistributionPackage>(
    dll = dll,
    serializer = DistributionPackage.serializer(),
    endpointPath = endpointPath,
    validator = validator
), DistributionPackage {

}