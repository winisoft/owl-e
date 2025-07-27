package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.schema.api.ApiEndpointDefinition
import com.wini.owle.schema.api.ApiOperationDefinition
import com.wini.owle.schema.wrapper.annotation.ApiEndpointAnnotation
import com.wini.owle.schema.wrapper.annotation.ApiEndpointAnnotationImpl


/**
 * Represents a class declaration annotated with [ApiEndpointDefinition], extracting all member functions
 * annotated with [com.wini.owle.schema.api.ApiOperationDefinition] and exposing the endpoint metadata.
 */
data class ApiEndpointDeclarationImpl(
    private val delegate: KSClassDeclaration
) : ApiEndpointDeclaration, KSClassDeclaration by delegate {

    override val operations: List<ApiOperationDeclaration> = delegate.getAllFunctions().filter { fnDeclaration ->
        fnDeclaration.annotations.any { anno ->
            anno.annotationType.resolve().declaration.qualifiedName?.asString() == ApiOperationDefinition.QUALIFIED_NAME
        }
    }.map { fnDeclaration ->
        ApiOperationDeclarationImpl(fnDeclaration)
    }.toList()

    override val apiEndpointAnnotation: ApiEndpointAnnotation = delegate.annotations
        .first {
            it.annotationType.resolve().declaration.qualifiedName?.asString() == ApiEndpointDefinition.QUALIFIED_NAME
        }.let { anno ->
            ApiEndpointAnnotationImpl(anno)
        }

    private val id: String = apiEndpointAnnotation.arguments.first { ksValue ->
        ksValue.name?.asString() == "id"
    }.value.toString()

}