package com.wini.owle.api.runs.test

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.BenchmarkReport
import io.ktor.server.application.*
import io.ktor.server.routing.*

import com.wini.owle.validators.DtoValidator


class BenchmarkReportApi(
    dll: DLL<ULong, BenchmarkReport>,
    endpointPath: String,
    validator: DtoValidator<BenchmarkReport>
): ULongIdEndpointImpl<BenchmarkReport>(
    dll = dll,
    endpointPath = endpointPath,
    serializer = BenchmarkReport.serializer(),
    validator = validator
)