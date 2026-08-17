package com.wini.owle.api.workflows

import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPoint
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.validators.DtoValidator


class DataPointsApiImpl(
    dll: DLL<ULong, DataPoint>,
    validator: DtoValidator<DataPoint>,
    override val endpointPath: String
): ULongIdEndpointImpl<DataPoint>(
    dll = dll,
    serializer = DataPoint.serializer(),
    endpointPath = endpointPath,
    validator = validator
) {

}