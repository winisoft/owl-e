package com.wini.owle.schema.wrapper.declaration.error

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.schema.error.ErrorDefinition
import com.wini.owle.schema.wrapper.annotation.error.ErrorAnnotation
import com.wini.owle.schema.wrapper.annotation.error.ErrorAnnotationImpl


data class ErrorDeclarationImpl(
    private val delegate: KSClassDeclaration
) : ErrorDeclaration, KSClassDeclaration by delegate {

    override val errorAnnotation: ErrorAnnotation
        get() = delegate.annotations.firstOrNull {
            it.annotationType.resolve().declaration.qualifiedName?.asString() == ErrorDefinition.QUALIFIED_NAME
        }?.let { it: KSAnnotation ->
            ErrorAnnotationImpl(it)
        } ?: error("ErrorDeclarationImpl was constructed with KSClassDeclaration '${delegate.simpleName.asString()}' which is not annotated with @${ErrorDefinition.QUALIFIED_NAME.substringAfterLast('.')}.")

    override val code: String
        get() = errorAnnotation.code

    override val message: String
        get() = errorAnnotation.message

    override val errorResKey: String
        get() = errorAnnotation.errorResKey

    override val fqName: String
        get() = qualifiedName?.asString().toString()
}