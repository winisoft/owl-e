package com.wini.owle.error

import kotlinx.serialization.Serializable

/**
 * An error whose origin is in the repository layer, which presumably has faithfully informed that the id that had
 * been referenced for selection or deletion did not exist. This indicates a disconnect between clients interacting
 * with the database and the application's efficacy representing the concurrent operations globally
 */
@Serializable
data object NoSuchIdError : OwleError {
    override val errorCode = "NO_SUCH_ID"
    override val message = "No such ID"
    override val detail = null
}