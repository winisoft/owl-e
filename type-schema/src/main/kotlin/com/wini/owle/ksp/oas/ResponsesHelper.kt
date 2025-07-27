package com.wini.owle.ksp.oas
import com.wini.owle.schema.wrapper.declaration.ApiOperationDeclaration
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
typealias SchemaResolver = (ApiOperationDeclaration) -> Schema<*>

class ResponsesHelper {

    fun buildForOperation(

    ): ApiResponses {
        val responses = ApiResponses()
        val response = ApiResponse().apply {
            description = "description"
        }

        return responses.addApiResponse("", response)

    }


}