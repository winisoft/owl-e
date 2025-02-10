package com.wini.owle.air.db.backoffice.call

sealed class HttpCode(val value: Long, val message: String) {

    object SwitchProtocol: com.wini.owle.air.db.backoffice.call.HttpCode(101L, "Switching Protocols")

    object Ok: com.wini.owle.air.db.backoffice.call.HttpCode(200L, "OK")

    object Created: com.wini.owle.air.db.backoffice.call.HttpCode(201L, "Created")

    object Accepted: com.wini.owle.air.db.backoffice.call.HttpCode(202L, "Accepted")

    object NoContent: com.wini.owle.air.db.backoffice.call.HttpCode(204L, "No Content")

    object PartialContent: com.wini.owle.air.db.backoffice.call.HttpCode(206L, "Partial Content")

    object MultiStatus: com.wini.owle.air.db.backoffice.call.HttpCode(207L, "Multi-Status")

    object Redirect: com.wini.owle.air.db.backoffice.call.HttpCode(301L, "Moved Permanently")

    /** Many user agents mishandle 302 in ways that violate the RFC1945 spec (i.e., redirect a POST to a GET).
     * 303 and 307 were added in RFC2616 to address; Prefer 303 and 307 if the calling user agent supports them.*/
    object Found: com.wini.owle.air.db.backoffice.call.HttpCode(302L, "Found")

    object RedirectSeeOther: com.wini.owle.air.db.backoffice.call.HttpCode(303L, "See Other")

    object NotModified: com.wini.owle.air.db.backoffice.call.HttpCode(304L, "Not Modified")

    object TemporaryRedirect: com.wini.owle.air.db.backoffice.call.HttpCode(307L, "Temporary Redirect")

    object BadRequest: com.wini.owle.air.db.backoffice.call.HttpCode(400L, "Bad Request")

    object Unauthorized: com.wini.owle.air.db.backoffice.call.HttpCode(401L, "Unauthorized")

    object Forbidden: com.wini.owle.air.db.backoffice.call.HttpCode(403L, "Forbidden")

    object NotFound: com.wini.owle.air.db.backoffice.call.HttpCode(404L, "Not Found")

    object MethodNotAllowed: com.wini.owle.air.db.backoffice.call.HttpCode(405L, "Method Not Allowed")

    object NotAcceptable: com.wini.owle.air.db.backoffice.call.HttpCode(406L, "Not Acceptable")

    object RequestTimeout: com.wini.owle.air.db.backoffice.call.HttpCode(408L, "Request Timeout")

    object Conflict: com.wini.owle.air.db.backoffice.call.HttpCode(409L, "Conflict")

    object Gone: com.wini.owle.air.db.backoffice.call.HttpCode(410L, "Gone")

    object LengthRequired: com.wini.owle.air.db.backoffice.call.HttpCode(411L, "Length Required")

    object PreconditionFailed: com.wini.owle.air.db.backoffice.call.HttpCode(412L, "Precondition Failed")

    object PayloadTooLarge: com.wini.owle.air.db.backoffice.call.HttpCode(413L, "Payload Too Large")

    object UnsupportedMediaType: com.wini.owle.air.db.backoffice.call.HttpCode(415L, "Unsupported Media Type")

    object RangeNotSatisfiable: com.wini.owle.air.db.backoffice.call.HttpCode(416L, "Requested Range Not Satisfiable")

    object ExpectationFailed: com.wini.owle.air.db.backoffice.call.HttpCode(417L, "Expectation Failed")

    object TooManyRequests: com.wini.owle.air.db.backoffice.call.HttpCode(429L, "Too Many Requests")

    object InternalError: com.wini.owle.air.db.backoffice.call.HttpCode(500L, "Internal Server Error")

    object NotImplemented: com.wini.owle.air.db.backoffice.call.HttpCode(501L, "Not Implemented")

    object ServiceUnavailable: com.wini.owle.air.db.backoffice.call.HttpCode(503L, "Service Unavailable")

    object UnsupportedHttpVersion: com.wini.owle.air.db.backoffice.call.HttpCode(505L, "HTTP Version Not Supported")

    companion object {

        fun fromLong(value: Long): com.wini.owle.air.db.backoffice.call.HttpCode = when(value) {
            com.wini.owle.air.db.backoffice.call.HttpCode.SwitchProtocol.value -> com.wini.owle.air.db.backoffice.call.HttpCode.SwitchProtocol
            com.wini.owle.air.db.backoffice.call.HttpCode.Ok.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Ok
            com.wini.owle.air.db.backoffice.call.HttpCode.Created.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Created
            com.wini.owle.air.db.backoffice.call.HttpCode.Accepted.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Accepted
            com.wini.owle.air.db.backoffice.call.HttpCode.NoContent.value -> com.wini.owle.air.db.backoffice.call.HttpCode.NoContent
            com.wini.owle.air.db.backoffice.call.HttpCode.PartialContent.value -> com.wini.owle.air.db.backoffice.call.HttpCode.PartialContent
            com.wini.owle.air.db.backoffice.call.HttpCode.MultiStatus.value -> com.wini.owle.air.db.backoffice.call.HttpCode.MultiStatus
            com.wini.owle.air.db.backoffice.call.HttpCode.Redirect.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Redirect
            com.wini.owle.air.db.backoffice.call.HttpCode.Found.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Found
            com.wini.owle.air.db.backoffice.call.HttpCode.RedirectSeeOther.value -> com.wini.owle.air.db.backoffice.call.HttpCode.RedirectSeeOther
            com.wini.owle.air.db.backoffice.call.HttpCode.NotModified.value -> com.wini.owle.air.db.backoffice.call.HttpCode.NotModified
            com.wini.owle.air.db.backoffice.call.HttpCode.TemporaryRedirect.value -> com.wini.owle.air.db.backoffice.call.HttpCode.TemporaryRedirect
            com.wini.owle.air.db.backoffice.call.HttpCode.BadRequest.value -> com.wini.owle.air.db.backoffice.call.HttpCode.BadRequest
            com.wini.owle.air.db.backoffice.call.HttpCode.Unauthorized.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Unauthorized
            com.wini.owle.air.db.backoffice.call.HttpCode.Forbidden.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Forbidden
            com.wini.owle.air.db.backoffice.call.HttpCode.NotFound.value -> com.wini.owle.air.db.backoffice.call.HttpCode.NotFound
            com.wini.owle.air.db.backoffice.call.HttpCode.MethodNotAllowed.value -> com.wini.owle.air.db.backoffice.call.HttpCode.MethodNotAllowed
            com.wini.owle.air.db.backoffice.call.HttpCode.NotAcceptable.value -> com.wini.owle.air.db.backoffice.call.HttpCode.NotAcceptable
            com.wini.owle.air.db.backoffice.call.HttpCode.RequestTimeout.value -> com.wini.owle.air.db.backoffice.call.HttpCode.RequestTimeout
            com.wini.owle.air.db.backoffice.call.HttpCode.Conflict.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Conflict
            com.wini.owle.air.db.backoffice.call.HttpCode.Gone.value -> com.wini.owle.air.db.backoffice.call.HttpCode.Gone
            com.wini.owle.air.db.backoffice.call.HttpCode.LengthRequired.value -> com.wini.owle.air.db.backoffice.call.HttpCode.LengthRequired
            com.wini.owle.air.db.backoffice.call.HttpCode.PreconditionFailed.value -> com.wini.owle.air.db.backoffice.call.HttpCode.PreconditionFailed
            com.wini.owle.air.db.backoffice.call.HttpCode.PayloadTooLarge.value -> com.wini.owle.air.db.backoffice.call.HttpCode.PayloadTooLarge
            com.wini.owle.air.db.backoffice.call.HttpCode.UnsupportedMediaType.value -> com.wini.owle.air.db.backoffice.call.HttpCode.UnsupportedMediaType
            com.wini.owle.air.db.backoffice.call.HttpCode.RangeNotSatisfiable.value -> com.wini.owle.air.db.backoffice.call.HttpCode.RangeNotSatisfiable
            com.wini.owle.air.db.backoffice.call.HttpCode.ExpectationFailed.value -> com.wini.owle.air.db.backoffice.call.HttpCode.ExpectationFailed
            com.wini.owle.air.db.backoffice.call.HttpCode.TooManyRequests.value -> com.wini.owle.air.db.backoffice.call.HttpCode.TooManyRequests
            com.wini.owle.air.db.backoffice.call.HttpCode.InternalError.value -> com.wini.owle.air.db.backoffice.call.HttpCode.InternalError
            com.wini.owle.air.db.backoffice.call.HttpCode.NotImplemented.value -> com.wini.owle.air.db.backoffice.call.HttpCode.NotImplemented
            com.wini.owle.air.db.backoffice.call.HttpCode.ServiceUnavailable.value -> com.wini.owle.air.db.backoffice.call.HttpCode.ServiceUnavailable
            com.wini.owle.air.db.backoffice.call.HttpCode.UnsupportedHttpVersion.value -> com.wini.owle.air.db.backoffice.call.HttpCode.UnsupportedHttpVersion
            else -> error("Unanticipated value")
        }
    }
}