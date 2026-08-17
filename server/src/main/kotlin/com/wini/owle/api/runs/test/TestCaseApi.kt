package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.test.TestCase
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TestCaseApi(
    dll: DLL<Long, TestCase>,
    validator: DtoValidator<TestCase>
): LongIdEndpointImpl<TestCase>(
    dll = dll,
    serializer = TestCase.serializer(),
    validator = validator
), ITestCaseApi  {

}