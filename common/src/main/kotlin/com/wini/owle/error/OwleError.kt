package com.wini.owle.error

import com.wini.owle.async.AsyncResult
import com.wini.owle.schema.error.OwleDocsError


/**
 * The single source of truth for all errors in the app. All errors are instances of this interface.
 */
interface OwleError {

    /**
     * A unique string identifier for errors across the app.
     */
    val errorCode: String

    /**
     * A simple human-readable explanation of the error. No attempt is made to provide the arguments of expected or actual values.
     */
    val message: String
    
    /**
     * Additional insights, such as the class name and/or unique identifier of some offending object, he context in which the error 
     * occurred, the value expected vs. confronted with, and so on. 
     * 
     * Where `message` only communicates what all instances of an error has in common, `detail` communicates the non-universal aspects
     * of a current presentation of the error.
     */
    val detail: String?


    /**
     * Convenience method called from the site of a detected AsyncError that points to its containing AsyncResult.Failure
     * type, which is what the function intends to return. In other words, AsyncError is a bundle of data elaborating
     * and error, and when one is arrived upon, it is almost always a logical dead-end from which a result-encapsulating
     * type will be resulting.
     */
    fun asFailure(): AsyncResult.Failure {
        return AsyncResult.Failure(this)
    }

}
