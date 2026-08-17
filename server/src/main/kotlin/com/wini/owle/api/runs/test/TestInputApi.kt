package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestInput
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TestInputApi(
    dll: DLL<Long, TestInput>,
    validator: DtoValidator<TestInput>
): LongIdEndpointImpl<TestInput>(
    dll = dll,
    serializer = TestInput.serializer(),
    validator = validator
), ITestInputApi  {

}