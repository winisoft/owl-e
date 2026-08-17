package com.wini.owle.api.environs

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.validators.DtoValidator


class PackageExtraApiImpl(
    dll: DLL<ULong, PackageExtra>,
    validator: DtoValidator<PackageDistRequirement>,
    override val endpointPath: String
) : ULongIdEndpointImpl<PackageDistRequirement>(
    dll = dll,
    serializer = PackageDistRequirement.serializer(),
    endpointPath = endpointPath,
    validator = validator
) {

}