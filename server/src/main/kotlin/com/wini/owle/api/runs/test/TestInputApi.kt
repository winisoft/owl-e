package com.wini.owle.api.runs.test

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestInput
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class TestInputApi(
    dll: DLL<ULong, TestInput>,
    override val endpointPath: String,
    validator: DtoValidator<TestInput>
): ULongIdEndpointImpl<TestInput>(
    dll = dll,
    endpointPath = endpointPath,
    serializer = TestInput.serializer(),
    validator = validator
)