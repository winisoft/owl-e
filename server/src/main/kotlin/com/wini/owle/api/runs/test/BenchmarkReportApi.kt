package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.BenchmarkReport
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall


class BenchmarkReportApi(
    dll: DLL<Long, BenchmarkReport>,
    validator: DtoValidator<BenchmarkReport>
): LongIdEndpointImpl<BenchmarkReport>(
    dll = dll,
    serializer = BenchmarkReport.serializer(),
    validator = validator
), IBenchmarkReportApi  {

}