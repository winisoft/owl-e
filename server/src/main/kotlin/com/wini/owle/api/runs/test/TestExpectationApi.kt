package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestExpectation
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TestExpectationApi(
    dll: DLL<Long, TestExpectation>,
    validator: DtoValidator<TestExpectation>
) : LongIdEndpointImpl<TestExpectation>(
    dll = dll,
    serializer = TestExpectation.serializer(),
    validator = validator
), ITestExpectationApi  {

}