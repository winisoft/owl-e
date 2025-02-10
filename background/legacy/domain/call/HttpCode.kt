package com.wini.owle.air.domain.call

sealed class HttpCode(val value: Long, val message: String) {

    object SwitchProtocol: HttpCode(101L, "Switching Protocols")

    object Ok: HttpCode(200L, "OK")

    object Created: HttpCode(201L, "Created")

    object Accepted: HttpCode(202L, "Accepted")

    object NoContent: HttpCode(204L, "No Content")

    object PartialContent: HttpCode(206L, "Partial Content")

    object MultiStatus: HttpCode(207L, "Multi-Status")

    object Redirect: HttpCode(301L, "Moved Permanently")

    /** Many user agents mishandle 302 in ways that violate the RFC1945 spec (i.e., redirect a POST to a GET).
     * 303 and 307 were added in RFC2616 to address; Prefer 303 and 307 if the calling user agent supports them.*/
    object Found: HttpCode(302L, "Found")

    object RedirectSeeOther: HttpCode(303L, "See Other")

    object NotModified: HttpCode(304L, "Not Modified")

    object TemporaryRedirect: HttpCode(307L, "Temporary Redirect")

    object BadRequest: HttpCode(400L, "Bad Request")

    object Unauthorized: HttpCode(401L, "Unauthorized")

    object Forbidden: HttpCode(403L, "Forbidden")

    object NotFound: HttpCode(404L, "Not Found")

    object MethodNotAllowed: HttpCode(405L, "Method Not Allowed")

    object NotAcceptable: HttpCode(406L, "Not Acceptable")

    object RequestTimeout: HttpCode(408L, "Request Timeout")

    object Conflict: HttpCode(409L, "Conflict")

    object Gone: HttpCode(410L, "Gone")

    object LengthRequired: HttpCode(411L, "Length Required")

    object PreconditionFailed: HttpCode(412L, "Precondition Failed")

    object PayloadTooLarge: HttpCode(413L, "Payload Too Large")

    object UnsupportedMediaType: HttpCode(415L, "Unsupported Media Type")

    object RangeNotSatisfiable: HttpCode(416L, "Requested Range Not Satisfiable")

    object ExpectationFailed: HttpCode(417L, "Expectation Failed")

    object TooManyRequests: HttpCode(429L, "Too Many Requests")

    object InternalError: HttpCode(500L, "Internal Server Error")

    object NotImplemented: HttpCode(501L, "Not Implemented")

    object ServiceUnavailable: HttpCode(503L, "Service Unavailable")

    object UnsupportedHttpVersion: HttpCode(505L, "HTTP Version Not Supported")

    companion object {

        fun fromLong(value: Long): HttpCode = when(value) {
            SwitchProtocol.value -> SwitchProtocol
            Ok.value -> Ok
            Created.value -> Created
            Accepted.value -> Accepted
            NoContent.value -> NoContent
            PartialContent.value -> PartialContent
            MultiStatus.value -> MultiStatus
            Redirect.value -> Redirect
            Found.value -> Found
            RedirectSeeOther.value -> RedirectSeeOther
            NotModified.value -> NotModified
            TemporaryRedirect.value -> TemporaryRedirect
            BadRequest.value -> BadRequest
            Unauthorized.value -> Unauthorized
            Forbidden.value -> Forbidden
            NotFound.value -> NotFound
            MethodNotAllowed.value -> MethodNotAllowed
            NotAcceptable.value -> NotAcceptable
            RequestTimeout.value -> RequestTimeout
            Conflict.value -> Conflict
            Gone.value -> Gone
            LengthRequired.value -> LengthRequired
            PreconditionFailed.value -> PreconditionFailed
            PayloadTooLarge.value -> PayloadTooLarge
            UnsupportedMediaType.value -> UnsupportedMediaType
            RangeNotSatisfiable.value -> RangeNotSatisfiable
            ExpectationFailed.value -> ExpectationFailed
            TooManyRequests.value -> TooManyRequests
            InternalError.value -> InternalError
            NotImplemented.value -> NotImplemented
            ServiceUnavailable.value -> ServiceUnavailable
            UnsupportedHttpVersion.value -> UnsupportedHttpVersion
            else -> error("Unanticipated value")
        }
    }
}