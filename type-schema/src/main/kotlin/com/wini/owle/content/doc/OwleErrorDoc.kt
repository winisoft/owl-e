package com.wini.owle.ksp.doc.models


/**
 * Represents the body of documentation related to a single OwleError type.
 *
 * @property errorCode A unique string identifier that exists for each error type in the app
 * @property message A simple human-readable explanation of the error common to all circumstances under which it is produced.
 * @property attributes A map of additional parameters associated with the error. This may include the arguments relevant to 
 *     some constraint that has been violated, the error code of some internal HTTP request made, or the id of some entity that was not found.
 */
data class OwleErrorDoc(
    val errorCode: String,
    val message: String,
    val attributes: Map<String, Any?> = emptyMap()
)