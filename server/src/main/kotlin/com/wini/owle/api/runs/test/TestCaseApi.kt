package com.wini.owle.api.runs.test

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.validators.DtoValidator


class TestCaseApi(
    dll: DLL<ULong, TestCase>,
    endpointPath: String,
    validator: DtoValidator<TestCase>
): ULongIdEndpointImpl<TestCase>(
    dll = dll,
    serializer = TestCase.serializer(),
    endpointPath = endpointPath,
    validator = validator
)