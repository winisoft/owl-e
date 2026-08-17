package com.wini.owle.error

import com.wini.owle.async.AsyncResult


/**
 * The single source of truth for all errors in the app. All errors are instances of this interface.
 */
interface OwleError {

    /**
     * A unique string identifier for errors across the app.
     */
    val errorCode: String

    /**
     * A simple human-readable explanation of the error, without any specific focus on the values at issue.
     */
    val message: String
    
    /**
     * Additional insights, such as the class name and/or unique identifier of some offending object, the context in
     * which the error occurred, the value expected vs. produced, the parameters that provided it, or whatever
     * additional detail can be reliably reported on.
     * 
     * Where `message` only communicates what the variety of instances of an error have in common, `detail`
     * communicates the distinctive expression of the current presentation of the error.
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
