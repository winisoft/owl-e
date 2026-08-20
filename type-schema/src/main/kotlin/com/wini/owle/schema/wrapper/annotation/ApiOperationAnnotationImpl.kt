package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.utils.ApiDocStrings
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.schema.wrapper.declaration.ApiOperationDeclarationImpl
import kotlin.collections.first


/**
 * Represents an annotation applied to an API operation (e.g. a specific method like GET or POST).
 *
 * Encodes method-level metadata for routing and documentation purposes.
 */
data class ApiOperationAnnotationImpl(
    val delegate: KSAnnotation,
    private val owner: KSClassDeclaration? = null
): ApiOperationAnnotation, KSAnnotation by delegate {

    constructor(functionDeclaration: KSFunctionDeclaration): this(
        ApiOperationDeclarationImpl(delegate = functionDeclaration).apiOpsAnnotation,
        functionDeclaration.parentDeclaration as? KSClassDeclaration
    )

    /**
     * The DTO's name, from the declaring class. The endpoint id is not a reliable source for it -
     * ids carry suffixes (`versionApi`, `callableParameterApi`) and stale names
     * (`stepRunLogLine` for `ExecutionNotification`), and are filed under the wrong area in
     * several cases (`api.user.tag`). Falling back to the id's last segment only matters when the
     * operation is read without its class.
     */
    private val dtoNameSingle: String by lazy {
        owner?.simpleName?.asString()
            ?: opResKey.substringAfterLast('.').replaceFirstChar { it.uppercase() }
    }

    private val dtoNamePlural: String by lazy { ApiDocStrings.pluralOf(dtoNameSingle) }

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

    override val summary: String by lazy { ApiDocStrings.summary(httpMethod, cardinality) }

    override val description: String by lazy {
        ApiDocStrings.description(httpMethod, cardinality, dtoNameSingle, dtoNamePlural)
    }
}