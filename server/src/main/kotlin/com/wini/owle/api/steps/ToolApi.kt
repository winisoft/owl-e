package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Tool
import io.ktor.server.application.*
import io.ktor.server.routing.*import com.wini.owle.validators.DtoValidator


class ToolApi(
    dll: DLL<Long, Tool>,
    validator: DtoValidator<Tool>
): LongIdEndpointImpl<Tool>(
    dll = dll,
    serializer = Tool.serializer(),
    validator = validator
) {

}