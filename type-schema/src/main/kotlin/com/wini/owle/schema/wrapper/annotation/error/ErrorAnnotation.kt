package com.wini.owle.schema.wrapper.annotation.error

import com.google.devtools.ksp.symbol.KSAnnotation


/**
 * Marker for annotations that represent structured API errors.
 *
 * Used to extract code and message metadata from annotation-based error definitions.
 */
interface ErrorAnnotation : KSAnnotation {

    /**
     * Resource key for the localized or display-ready error message.
     */
    val errorResKey: String

    /**
     * Stable error code used for programmatic error handling and documentation.
     */
    val code: String

    /**
     * Human-readable explanation of the error’s cause.
     */
    val message: String

}