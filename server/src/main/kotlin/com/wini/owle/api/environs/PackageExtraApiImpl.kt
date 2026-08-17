package com.wini.owle.api.environs

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.pkg.PackageExtra
import com.wini.owle.validators.DtoValidator


class PackageExtraApiImpl(
    dll: DLL<ULong, PackageExtra>,
    validator: DtoValidator<PackageExtra>,
    override val endpointPath: String
) : ULongIdEndpointImpl<PackageExtra>(
    dll = dll,
    serializer = PackageExtra.serializer(),
    endpointPath = endpointPath,
    validator = validator
)