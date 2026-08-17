package com.wini.owle.api.runs.test

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestExpectation
import io.ktor.server.application.*
import io.ktor.server.routing.*

import com.wini.owle.validators.DtoValidator


class TestExpectationApi(
    dll: DLL<ULong, TestExpectation>,
    endpointPath: String,
    validator: DtoValidator<TestExpectation>
) : ULongIdEndpointImpl<TestExpectation>(
    dll = dll,
    endpointPath = endpointPath,
    serializer = TestExpectation.serializer(),
    validator = validator
)