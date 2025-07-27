package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.schema.wrapper.declaration.ApiOperationDeclarationImpl
import kotlin.collections.first


/**
 * Represents an annotation applied to an API operation (e.g. a specific method like GET or POST).
 *
 * Encodes method-level metadata for routing and documentation purposes.
 */
data class ApiOperationAnnotationImpl(
    val delegate: KSAnnotation
): ApiOperationAnnotation, KSAnnotation by delegate {

    constructor(functionDeclaration: KSFunctionDeclaration): this(
        ApiOperationDeclarationImpl(delegate = functionDeclaration).apiOpsAnnotation
    )

    override val opResKey: String = delegate.arguments.first { ksValue ->
        ksValue.name?.asString() == "opResKey"
    }.value.toString()

    private val key: String by lazy { "$opResKey.$httpMethod.$cardinality" }

    override val httpMethod: String by lazy {
        delegate.arguments.first {
            it.name?.asString() == "method"
        }.value.toString().substringAfterLast('.').lowercase()
    }

    override val cardinality: String by lazy {
        delegate.arguments.first {
            it.name?.asString() == "result"
        }.value.toString().substringAfterLast('.').lowercase()
    }

    override val path: String by lazy { LocalizedStringResolver.resolve("$key.path") }

    override val summary: String by lazy { LocalizedStringResolver.resolve("$key.summary") }

    override val description: String by lazy { LocalizedStringResolver.resolve("$key.description") }
}