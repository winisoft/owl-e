package com.wini.owle.schema.wrapper.declaration.error

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.schema.wrapper.annotation.error.ErrorAnnotation

/**
 * A wrapper around the KSClassDeclaration for a type that KSP found in the discovery of classes decorated with the
 * @ErrorDefinition annotation on their declaration.
 *
 * This is a 'common' module's declaration of a type that defines some error in the Owl-E application, each of which
 * has the OwleError interface as its base class.
 */
interface ErrorDeclaration: KSClassDeclaration {

    /**
     * The annotation that decorated this class declaration to mark it as a subject for KSP
     */
    val errorAnnotation: ErrorAnnotation

    /**
     * A value read from the arguments on the [ErrorAnnotation] that decorated this class that points to a key in
     * the module's resource bundle by which other localized values are resolved.
     */
    val errorResKey: String

    /**
     * The unique code belonging to all error-representing types in the Owl-E app. This value is resolved from
     * resources from a key derived by building onto the [errorResKey] property.
     */
    val code: String

    /**
     * A summary in clear general terms that explains the meaning of the underlying error type. This value is resolved
     * from resources from a key derived by building onto the [errorResKey] property.
     */
    val message: String

    val fqName: String
}