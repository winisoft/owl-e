package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.wini.owle.schema.api.ApiOperationDefinition
import com.wini.owle.schema.wrapper.annotation.ApiOperationAnnotation
import com.wini.owle.schema.wrapper.annotation.ApiOperationAnnotationImpl


/**
 * Concrete implementation of [ApiOperationDeclaration] that wraps a KSP-discovered function with
 * the [ApiOperationDefinition] annotation and exposes typed access to annotation metadata.
 */
data class ApiOperationDeclarationImpl(
    val delegate: KSFunctionDeclaration
): ApiOperationDeclaration,
    KSFunctionDeclaration by delegate {

    override val apiOpsAnnotation: ApiOperationAnnotation
        get() = delegate.annotations.first {
            it.annotationType.resolve().declaration.qualifiedName?.asString() == ApiOperationDefinition.QUALIFIED_NAME
        }.let {
            ApiOperationAnnotationImpl(it)
        }

    override val opResKey: String
        get() = apiOpsAnnotation.opResKey

    override val summary: String
        get() = apiOpsAnnotation.summary

    override val description: String
        get() = apiOpsAnnotation.description

    override val path: String
        get() = apiOpsAnnotation.path


}